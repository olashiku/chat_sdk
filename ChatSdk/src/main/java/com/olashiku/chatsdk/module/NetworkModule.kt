package com.olashiku.chatsdk.module

import android.app.Application
import com.olashiku.chatsdk.network.Socket
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getStringPref
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val network = module{
//    single {
//        createRubiesSocket<Socket>(okHttpClient(),androidApplication())
//    }

    single {
        createRubiesSocket<Socket>(okHttpClient(get()),androidApplication(), paperPrefs = get())
    }



}

inline fun <reified T>  createRubiesSocket (okHttpClient: OkHttpClient, application: Application, paperPrefs: PaperPrefs) : T {
    val BACKOFF_STRATEGY = LinearBackoffStrategy(8000)
    val scarletInstance = Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(getRubiesSocketUrl(paperPrefs.getStringPref(PaperPrefs.USERID))))
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
        .addHeader("Authorization", paperPrefs.getStringPref(PaperPrefs.USERID))
        // .addHeader("conversationId", paperPrefs.getStringPref(PaperPrefs.CONVERSATION_TOKEN))
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

private fun createAndroidLifecycle(application: Application): Lifecycle {
    return AndroidLifecycle.ofApplicationForeground(application)
}

private val jsonMoshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// A Retrofit inspired WebSocket client for Kotlin, Java, and Android, that supports websockets.
private fun createScarlet(okHttpClient: OkHttpClient, lifecycle: Lifecycle): Socket {
    return Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory("wss://bshy152j23.execute-api.eu-central-1.amazonaws.com/dev"))
        .lifecycle(lifecycle)
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory(jsonMoshi))
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .build()
        .create()
}
