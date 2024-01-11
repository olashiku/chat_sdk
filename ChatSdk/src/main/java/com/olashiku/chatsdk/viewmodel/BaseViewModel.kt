package com.olashiku.chatsdk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olashiku.chatsdk.network.SingleLiveEvent
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent
import kotlinx.coroutines.*

open class BaseViewModel: ViewModel() {

    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val paperPrefs: PaperPrefs by KoinJavaComponent.inject(PaperPrefs::class.java)
    fun <R:Any,T:Any> apiRequestNew(
        request:R, apiCall:suspend (request:R)-> UseCaseResult<T>, observer: SingleLiveEvent<T>, getError: (response: T) -> String,
        displayLoading:Boolean = true, showErrorMessage:Boolean = true,
        onErrorObserver: SingleLiveEvent<Unit>? = null, customLoader:SingleLiveEvent<Boolean>? = null){
        if(displayLoading)  {
            customLoader?.let {
                it.value = true
            } ?: run {
                showLoading.value = true
            }
        }

        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){apiCall(request)}
            if(displayLoading){
                customLoader?.let {
                    it.value = false
                } ?: run {
                    showLoading.value = false
                }

            }
            when(response){
                is UseCaseResult.Success -> observer.value = response.data
                is UseCaseResult.FailedAPI -> {
                    if(showErrorMessage) showError.value = getError(response.data)
                    onErrorObserver?.call()
                }
                is UseCaseResult.Error -> {
                    if(showErrorMessage) showError.value = response.exception.message
                    onErrorObserver?.call()
                }

                else -> {}
            }
        }
    }
}