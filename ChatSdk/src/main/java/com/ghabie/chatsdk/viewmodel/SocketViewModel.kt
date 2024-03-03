package com.ghabie.chatsdk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ghabie.chatsdk.model.Constants
import com.ghabie.chatsdk.model.NetworkActions
import com.ghabie.chatsdk.model.request.auth.LoginRequest
import com.ghabie.chatsdk.model.request.auth.MsgSender
import com.ghabie.chatsdk.model.request.connections.ConnectionRequest
import com.ghabie.chatsdk.model.request.connections.Data
import com.ghabie.chatsdk.model.request.get_messages.GetMessagesRequest
import com.ghabie.chatsdk.model.request.get_messages.Query
import com.ghabie.chatsdk.model.request.new_message.Message
import com.ghabie.chatsdk.model.request.new_message.MsgReceiver
import com.ghabie.chatsdk.model.request.new_message.NewMessageRequest
import com.ghabie.chatsdk.model.request.typing.TypingRequest
import com.ghabie.chatsdk.model.response.login.Connection
import com.ghabie.chatsdk.repository.SocketRepository
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.getAnyPref
import com.ghabie.chatsdk.storage.getStringPref
import com.ghabie.chatsdkandroid.utils.Utils
import kotlinx.coroutines.launch

class SocketViewModel(private val socketRepository: SocketRepository):BaseViewModel() {

    fun initSocket(){
        viewModelScope.launch {
            socketRepository.startSocket()
        }
    }

     fun loginUser(){
         val sender = MsgSender(
             Utils.getDeviceDetails(), paperPrefs.getStringPref(PaperPrefs.ORGID)?:"", paperPrefs.getStringPref(
                 PaperPrefs.USERID)?:"")
         val request = LoginRequest(
             NetworkActions.auth, "1232a23",
             "{lat:38.8951, log:-77.0364}", "Android", sender, Utils.getCurrentUnixTimestamp().toString()
         )
         socketRepository.loginUser(request)
     }

    fun getMessages(agentUserName:String){
        val query = Query(paperPrefs.getStringPref(PaperPrefs.USERID)?:"",paperPrefs.getStringPref(PaperPrefs.ORGID)?:"","0","10",agentUserName)
        val request = GetMessagesRequest(NetworkActions.getMessage,Utils.getUniqueRef(),query)
        socketRepository.getMessage(request)
    }

    fun newMessage(text:String, messageType:String,currentTimeStamp:Long) {
        val recipient = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)
        val message = Message(text, Constants.platform, messageType)
        val receiver = MsgReceiver("", recipient?.agentUserName?:"")
        val sender = com.ghabie.chatsdk.model.request.new_message.MsgSender(
            Utils.getDeviceDetails(),
            paperPrefs.getStringPref(PaperPrefs.ORGID)?:"",
            paperPrefs.getStringPref(PaperPrefs.USERID)?:""
        )
        val request = NewMessageRequest(NetworkActions.newMessage, message, Utils.getUniqueRef(), Constants.platform, receiver, sender,currentTimeStamp)
        println("request ${request}")
        socketRepository.newMessage(request)
    }

     fun typingMessages(status:Boolean){
         val recipient = paperPrefs.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)
         val receiver = com.ghabie.chatsdk.model.request.typing.MsgReceiver(Utils.getUniqueRef(), recipient?.agentUserName?:"")
         val sender = com.ghabie.chatsdk.model.request.typing.MsgSender(
             Utils.getDeviceDetails(),
             paperPrefs.getStringPref(PaperPrefs.ORGID)?:"",
             paperPrefs.getStringPref(PaperPrefs.USERID)?:"",
             status
         )
         val request = TypingRequest(NetworkActions.typing,"", Constants.platform,receiver,sender,Utils.getCurrentUnixTimestamp())

         socketRepository.typingMessage(request)
     }

    fun getConnection(){
        val request = ConnectionRequest(NetworkActions.connection, Data(  paperPrefs.getStringPref(PaperPrefs.ORGID)?:"",  paperPrefs.getStringPref(PaperPrefs.USERID)?:""),Utils.getUniqueRef())
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