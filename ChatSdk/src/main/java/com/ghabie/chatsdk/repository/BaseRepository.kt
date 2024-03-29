package com.ghabie.chatsdk.repository

import com.google.gson.Gson
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.utils.UseCaseResult
import kotlinx.coroutines.Deferred
import org.koin.java.KoinJavaComponent
import retrofit2.HttpException
import timber.log.Timber

open class BaseRepository {

    val paperPref: PaperPrefs by KoinJavaComponent.inject(PaperPrefs::class.java)
    suspend inline fun <R:Any, reified T:Any> safeApiCall(request: R, apiCall: (request:R) -> Deferred<T>, checkIfSuccessful:(T)->Boolean): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        } catch (ex:Exception){
            when(ex){
                is HttpException -> {
                    try {
                        Timber.e(ex)
                        val body = ex.response()?.errorBody()
                        val errorJson = Gson().fromJson(body?.string() ?: "", T::class.java)
                        UseCaseResult.FailedAPI(errorJson)
                    }catch (ex:java.lang.Exception){
                        Timber.e(ex)
                        UseCaseResult.Error(ex)
                    }
                }
                else -> {
                    Timber.e(ex)
                    UseCaseResult.Error(ex)
                }
            }

        }
    }
    suspend fun <T:Any> safeGetApiCallAndNormalOperation(apiCall: () ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:(response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall().await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <R:Any,T:Any> safeApiCallUpdate(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean, onSuccessOperations:suspend (response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    suspend fun <R:Any,T:Any> safeApiCallAndNormalOperation(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean, onSuccessOperations: (response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    suspend fun <R:Any,T:Any> safeApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:(response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <R:Any,T:Any> safeApiCalls(request:R,apiCall:(R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:(response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    suspend fun <R:Any,T:Any> safeApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean, onSuccessOperations:suspend (response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }
    suspend fun <R:Any,T:Any> safeApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean, onSuccessOperations:suspend (request:R,response:T)->Unit): UseCaseResult<T> {
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(request,response)
                }catch (ex:Exception){
                    Timber.e(ex)
                }
                UseCaseResult.Success(response)
            }else{
                UseCaseResult.FailedAPI(response)
            }
        }catch (ex:Exception){
            Timber.e(ex)
            UseCaseResult.Error(ex)
        }
    }

    suspend fun <R:Any,T:Any> safeWorkerApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean):Boolean{
        return try {
            val response = apiCall(request).await()
            checkIfSuccessful(response)
        }catch (ex:Exception){
            Timber.e(ex)
            false
        }
    }
    suspend fun <R:Any,T:Any> safeWorkerApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:(response:T)->Unit):Boolean{
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                onSuccessOperations(response)
                true
            }else{
                false
            }
        }catch (ex:Exception){
            Timber.e(ex)
            false
        }
    }
    suspend fun <R:Any,T:Any> safeWorkerApiCall(request: R,apiCall: (request:R) ->Deferred<T>, checkIfSuccessful:(T)->Boolean,onSuccessOperations:suspend (response:T)->Unit):Boolean{
        return try {
            val response = apiCall(request).await()
            if(checkIfSuccessful(response)){
                try {
                    onSuccessOperations(response)
                }catch (ex:java.lang.Exception){
                    Timber.e(ex)
                }

                true
            }else{
                false
            }
        }catch (ex:Exception){
            Timber.e(ex)
            false
        }
    }

}
suspend fun <R:Any,T:Any> safeApiCall(request: R, apiCall: (request:R) -> Deferred<T>, checkIfSuccessful:(T)->Boolean): UseCaseResult<T> {
    return try {
        val response = apiCall(request).await()
        when {
            checkIfSuccessful(response) -> {
                UseCaseResult.Success(response)
            }
            else -> {
                UseCaseResult.FailedAPI(response)
            }
        }
    }catch (ex:Exception){
        Timber.e(ex)
        UseCaseResult.Error(ex)
    }
}