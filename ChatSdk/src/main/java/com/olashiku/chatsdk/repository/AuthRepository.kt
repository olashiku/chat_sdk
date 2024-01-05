package com.olashiku.chatsdk.repository

import androidx.lifecycle.MutableLiveData
import com.olashiku.chatsdk.model.NetworkStatus
import com.olashiku.chatsdk.model.request.auth.LoginRequest
import com.olashiku.chatsdk.model.response.login.LoginResponse
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.savePref
import com.olashiku.chatsdkandroid.utils.getObject


interface AuthRepository {
    fun loginUserResponse(loginResponse: String)
    fun getLoginResponse():MutableLiveData<LoginResponse>
}


class AuthRepositoryImpl : BaseRepository(), AuthRepository {

    val loginResponseLiveData = MutableLiveData<LoginResponse>()

    override fun loginUserResponse(loginResponse: String){
        val response = loginResponse.getObject<LoginResponse>()
        if(response.status.equals(NetworkStatus.allow)){
            paperPref.savePref(PaperPrefs.USERID, response.data?.userid ?: "")
            paperPref.savePref(PaperPrefs.ORGID, response.data?.orgid ?: "")
            paperPref.savePref(PaperPrefs.SESSIONID, response.data?.sessionid ?: "")
            paperPref.savePref(PaperPrefs.USERSTATUS, response.data?.userStatus ?: "")
            paperPref.savePref(PaperPrefs.CONNECTIONDETAILS, response.data?.connection!!)
            paperPref.savePref(PaperPrefs.USERUUID,response.data.useruuid?:"")
            loginResponseLiveData.postValue(response)
        }

    }

    override fun getLoginResponse(): MutableLiveData<LoginResponse> {
       return loginResponseLiveData
    }


}