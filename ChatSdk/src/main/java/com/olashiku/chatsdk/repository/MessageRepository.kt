package com.olashiku.chatsdk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.olashiku.chatsdk.model.Constants
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdk.model.response.get_messages.GetMessagesResponse
import com.olashiku.chatsdk.model.response.login.Connection
import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.model.response.message_status.MessageStatusResponse
import com.olashiku.chatsdk.model.response.new_message.NewMessageResponse
import com.olashiku.chatsdk.model.response.online_offline.OnlineOfflineStatusResponse
import com.olashiku.chatsdk.model.response.typing.TypingResponse
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getAnyPref
import com.olashiku.chatsdk.storage.getStringPref
import com.olashiku.chatsdk.storage.room.datasource.MessageDataSource
import com.olashiku.chatsdkandroid.utils.getObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking


interface MessageRepository {
    fun getMessageResponse(message: String)
    fun messageDeliveredResponse(message: String)
    fun newMessage(request: NewMessageRequest)
    fun newMessageResponse(message: String)
    fun onlineResponse(message: String)
    fun getOnlineOfflineStatus():MutableLiveData<OnlineOfflineStatusResponse>

    fun typingMessageResponse(request: String)
    fun getMessagesFromDatabase(): Flow<List<Messages>>
    fun getUnsentMessages(): List<Messages>
    fun getTypingMessageFlow(): Flow<TypingResponse>
    fun getTypingMessageMutableLiveData(): LiveData<TypingResponse>
}

class MessageRepositoryImpl(
    private val messagesDataSource: MessageDataSource
) : BaseSocketRepository(), MessageRepository {

     var typingMessage: TypingResponse? = null
    val typingLiveData = MutableLiveData<TypingResponse>()
    val onlineLiveData  = MutableLiveData<OnlineOfflineStatusResponse>()


    override fun getMessageResponse(message: String) {
        val connection = paperPref.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)
        val response = message.getObject<GetMessagesResponse>()
        val recipient = paperPref.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)

        if (response.data?.content?.isNotEmpty() == true) {
            val messageResponse = message.getObject<GetMessagesResponse>()
            val messages = messageResponse.data
            messagesDataSource.deleteMessages()
            runBlocking(Dispatchers.IO) {
                messages?.content?.let { request ->
                    request.reversed().map { content ->
                        messagesDataSource.updateMessage(
                            Messages(
                                0,
                                content.id ?: "",
                                content.feTimeStamp ?: 0,
                                content.senderId ?: "",
                                content.recipientId ?: "",
                                content.msgType ?: "",
                                false,
                                content.body ?: "",
                                if (content.senderId.equals(connection?.userId)) Constants.me else Constants.you,
                                Constants.messageUnread,
                                content.orgId ?: "",
                                content.orgId ?: "",
                                recipient?.agentUserName ?: ""
                            )
                        )
                    }
                }
            }
        }
    }

    override fun messageDeliveredResponse(message: String) {
        val response = message.getObject<MessageStatusResponse>()
        if (response.data?.reason?.equals(Constants.userReachable) == true) {
            messagesDataSource.updateMessageById(response.data.msgId ?: "")
        }
    }

    override fun newMessage(request: NewMessageRequest) {
        runBlocking(Dispatchers.IO) {
            val recipient = paperPref.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)

            messagesDataSource.updateMessage(
                Messages(
                    0,
                    request.msgId,
                    request.msgTimeStamp,
                    request.msgSender.userId,
                    request.msgReceiver?.userId ?: "",
                    request.message.type, false,
                    request.message.content,
                    Constants.me,
                    Constants.messageUnread,
                    paperPref.getStringPref(PaperPrefs.ORGID)?:"",
                    paperPref.getStringPref(PaperPrefs.USERID)?:"",
                    recipient?.agentUserName ?: ""

                )
            )
        }

    }

    override fun newMessageResponse(message: String) {
        println("newMessage $message")
        val response = message.getObject<NewMessageResponse>()
        val recipient = paperPref.getAnyPref<Connection>(PaperPrefs.CONNECTIONDETAILS)

        runBlocking(Dispatchers.IO) {
            messagesDataSource.updateMessage(
                Messages(
                    0,
                    response.msgId ?: "",
                    response.msgTimeStamp ?: 0,
                    response.msgSender?.userId ?: "",
                    paperPref.getStringPref(PaperPrefs.USERUUID)?:"",
                    response.message?.type ?: "",
                    true,
                    response.message?.content ?: "",
                    Constants.you,
                    Constants.messageRead,
                    paperPref.getStringPref(PaperPrefs.ORGID)?:"",
                    paperPref.getStringPref(PaperPrefs.USERID)?:"",
                    recipient?.agentUserName ?: ""

                )
            )
        }
    }

    override fun onlineResponse(message: String) {
        val onlineOffline = message.getObject<OnlineOfflineStatusResponse>()
        onlineLiveData.postValue(onlineOffline)
    }

    override fun getOnlineOfflineStatus():MutableLiveData<OnlineOfflineStatusResponse> = onlineLiveData

    override fun typingMessageResponse(request: String) {
        val typingResponse = request.getObject<TypingResponse>()
        println("typing_response $typingResponse")
        typingLiveData.postValue(typingResponse)
        typingMessage = typingResponse

    }

   override fun getTypingMessageFlow(): Flow<TypingResponse> {
        return flowOf(typingMessage?: TypingResponse())
    }

    override fun getTypingMessageMutableLiveData(): LiveData<TypingResponse> {
        return typingLiveData
    }

    override fun getMessagesFromDatabase(): Flow<List<Messages>> {
        return messagesDataSource.getAllMessages()
    }

    override fun getUnsentMessages(): List<Messages> {
        return messagesDataSource.getUnsentMessages()
    }

}