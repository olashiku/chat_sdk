package com.ghabie.chatsdk.repository

import androidx.lifecycle.MutableLiveData
import com.ghabie.chatsdk.model.NetworkStatus
import com.ghabie.chatsdk.model.response.login.LoginResponse
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.savePref
import com.ghabie.chatsdkandroid.utils.getObject


interface AuthRepository {
    fun loginUserResponse(loginResponse: String)
    fun getLoginResponse():MutableLiveData<LoginResponse>
}


class AuthRepositoryImpl : BaseSocketRepository(), AuthRepository {

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
        }
        loginResponseLiveData.postValue(response)
    }

    override fun getLoginResponse(): MutableLiveData<LoginResponse> {
       return loginResponseLiveData
    }


}