package com.ghabie.chatsdk.module

import android.app.Application
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ghabie.chatsdk.network.Api
import com.ghabie.chatsdk.network.Socket
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.getStringPref
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val DEV_BASE_URL = "https://api.ghabie.com/"
val network = module{
    single {
        createWebService<Api>(
            RxJava2CallAdapterFactory.create(),
            DEV_BASE_URL,get())
    }

    single {
        createRubiesSocket<Socket>(okHttpClient(get()),androidApplication(), paperPrefs = get())
    }



}

inline fun <reified T> createWebService(
    factory: CallAdapter.Factory, baseUrl: String, paperPrefs: PaperPrefs
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient(paperPrefs))
        .build()
    return retrofit.create(T::class.java)
}

inline fun <reified T>  createRubiesSocket (okHttpClient: OkHttpClient, application: Application, paperPrefs: PaperPrefs) : T {
    val BACKOFF_STRATEGY = LinearBackoffStrategy(500)
    val scarletInstance = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(getRubiesSocketUrl(paperPrefs.getStringPref(PaperPrefs.USERID)?:"")))
        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .backoffStrategy(BACKOFF_STRATEGY)
        .lifecycle(createAppForegroundAndUserLoggedInLifecycle(application))
        .build()

    return scarletInstance.create(T::class.java)

}

fun getRubiesSocketUrl (token : String) :String {
    return "wss://bshy152j23.execute-api.eu-central-1.amazonaws.com/dev"

}


fun createAppForegroundAndUserLoggedInLifecycle(application: Application): Lifecycle {
    return AndroidLifecycle.ofApplicationForeground(application)
}

fun okHttpClient(paperPrefs: PaperPrefs) =
    OkHttpClient.Builder()
        .addInterceptor(headersInterceptor(paperPrefs))
        .addInterceptor(loggingInterceptor())
        .readTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(  5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .build()

fun loggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun headersInterceptor(paperPrefs: PaperPrefs) = Interceptor { chain ->
    chain.proceed(chain.request().newBuilder()
        .addHeader("Content-Type", "application/json")
        .build())

}


private fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}
