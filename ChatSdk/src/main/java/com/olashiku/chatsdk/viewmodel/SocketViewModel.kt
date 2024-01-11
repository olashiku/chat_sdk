package com.olashiku.chatsdk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.olashiku.chatsdk.model.Constants
import com.olashiku.chatsdk.model.NetworkActions
import com.olashiku.chatsdk.model.request.auth.LoginRequest
import com.olashiku.chatsdk.model.request.auth.MsgSender
import com.olashiku.chatsdk.model.request.connections.ConnectionRequest
import com.olashiku.chatsdk.model.request.connections.Data
import com.olashiku.chatsdk.model.request.get_messages.GetMessagesRequest
import com.olashiku.chatsdk.model.request.get_messages.Query
import com.olashiku.chatsdk.model.request.new_message.Message
import com.olashiku.chatsdk.model.request.new_message.MsgReceiver
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdk.model.request.typing.TypingRequest
import com.olashiku.chatsdk.model.response.login.Connection
import com.olashiku.chatsdk.repository.SocketRepository
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getAnyPref
import com.olashiku.chatsdk.storage.getStringPref
import com.olashiku.chatsdkandroid.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import timber.log.Timber

class SocketViewModel(private val socketRepository: SocketRepository):BaseViewModel() {

    fun initSocket(){
        viewModelScope.launch {
            socketRepository.startSocket()
        }
    }

     fun loginUser(){
         val sender = MsgSender(
             Utils.getDeviceDetails(), paperPrefs.getStringPref(PaperPrefs.ORGID), paperPrefs.getStringPref(
                 PaperPrefs.USERID))
         val request = LoginRequest(
             NetworkActions.auth, "1232a23",
             "{lat:38.8951, log:-77.0364}", "Android", sender, "1698495947"
         )
         socketRepository.loginUser(request)
     }

    fun getMessages(agentUserName:String){
        val query = Query(paperPrefs.getStringPref(PaperPrefs.USERID),paperPrefs.getStringPref(PaperPrefs.ORGID),"0","10",agentUserName)
        val request = GetMessagesRequest(NetworkActions.getMessage,Utils.getUniqueRef(),query)
        socketRepository.getMessage(request)
    }

    fun newMessage(text:String, messageType:String) {
        val recipient = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)
        val message = Message(text, Constants.platform, messageType)
        val receiver = MsgReceiver("", recipient.agentUserName?:"")
        val sender = com.olashiku.chatsdk.model.request.new_message.MsgSender(
            Utils.getDeviceDetails(),
            paperPrefs.getStringPref(PaperPrefs.ORGID),
            paperPrefs.getStringPref(PaperPrefs.USERID)
        )
        val request = NewMessageRequest(NetworkActions.newMessage, message, Utils.getUniqueRef(), Constants.platform, receiver, sender, 0)
        socketRepository.newMessage(request)
    }

     fun typingMessages(status:Boolean){
         val recipient = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)
         val receiver = com.olashiku.chatsdk.model.request.typing.MsgReceiver("", recipient.userId?:"")
         val sender = com.olashiku.chatsdk.model.request.typing.MsgSender(
             Utils.getDeviceDetails(),
             paperPrefs.getStringPref(PaperPrefs.ORGID),
             paperPrefs.getStringPref(PaperPrefs.USERID),
             status
         )
         val request = TypingRequest(NetworkActions.typing,"", Constants.platform,receiver,sender,"")

         socketRepository.typingMessage(request)
     }

    fun getConnection(){
        val request = ConnectionRequest(NetworkActions.connection, Data(  paperPrefs.getStringPref(PaperPrefs.ORGID),  paperPrefs.getStringPref(PaperPrefs.USERID)),Utils.getUniqueRef())
        socketRepository.getConnection(request)
    }


    fun destroySocket(){
        socketRepository.destroySocket()
    }

    fun checkSocketConnection():Boolean{
        return socketRepository.isSocketAlive()
    }

     fun getConnectionStatus():MutableLiveData<Boolean>{
         return socketRepository.getConnectionStatus()
     }
}