package com.ghabie.chatsdk.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ghabie.chatsdk.model.response.login.LoginResponse
import com.ghabie.chatsdk.repository.AuthRepository

class AuthenticationViewModel(val authRepository: AuthRepository) : BaseViewModel() {

    fun getLoginResponse(): MutableLiveData<LoginResponse> {
        return authRepository.getLoginResponse()
    }

}