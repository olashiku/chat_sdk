package com.olashiku.chatsdk.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.olashiku.chatsdk.model.NetworkActions
import com.olashiku.chatsdk.model.request.auth.LoginRequest
import com.olashiku.chatsdk.model.request.auth.MsgSender
import com.olashiku.chatsdk.model.response.login.LoginResponse
import com.olashiku.chatsdk.repository.AuthRepository
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getStringPref
import com.olashiku.chatsdkandroid.utils.Utils

class AuthenticationViewModel(val authRepository: AuthRepository) : BaseViewModel() {

    fun getLoginResponse(): MutableLiveData<LoginResponse> {
        return authRepository.getLoginResponse()
    }

}