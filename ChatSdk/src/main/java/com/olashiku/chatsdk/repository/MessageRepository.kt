package com.olashiku.chatsdk.repository

import com.olashiku.chatsdk.model.Constants
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdk.model.response.get_messages.GetMessagesIntroResponse
import com.olashiku.chatsdk.model.response.get_messages.GetMessagesResponse
import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.model.response.message_status.MessageStatusResponse
import com.olashiku.chatsdk.storage.room.datasource.MessageDataSource
import com.olashiku.chatsdkandroid.utils.getObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking


interface MessageRepository {
    fun getMessageResponse(message: String)
    fun messageDeliveredResponse(message: String)
    fun newMessage(request: NewMessageRequest)
    fun typingMessageResponse(request: String)
    fun getMessagesFromDatabase(): Flow<List<Messages>>
}

class MessageRepositoryImpl(
    private val messagesDataSource: MessageDataSource
) : BaseRepository(), MessageRepository {

    override fun getMessageResponse(message: String) {
        val response = message.getObject<GetMessagesIntroResponse>()
        if (response.data.isNotEmpty()) {
            val messageResponse = message.getObject<GetMessagesResponse>()
            val messages = messageResponse.data
            runBlocking(Dispatchers.IO) {
                messages?.content?.let { request ->
                    request.map { content ->
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
                                Constants.you,
                                Constants.messageUnread
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
                    Constants.messageUnread
                )
            )
        }

    }

    override fun typingMessageResponse(request: String) {
        // TODO: use mutable live data to persist this
    }


    override fun getMessagesFromDatabase(): Flow<List<Messages>> {
        return messagesDataSource.getAllMessages()
    }

}