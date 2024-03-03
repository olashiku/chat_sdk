package com.ghabie.chatsdk.storage.room.datasource

import com.ghabie.chatsdk.model.response.message.Messages
import com.ghabie.chatsdk.storage.room.dao.MessageDao
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
   fun getAllMessages(): Flow<List<Messages>>
   fun getUnsentMessages():List<Messages>
   fun updateMessages(messages:List<Messages>)
    fun updateMessage(messages:Messages)
    fun updateMessageById(messageid:String)

    fun deleteMessages()
}

class MessageDataSourceImpl(val messageDao:MessageDao):MessageDataSource{


    override fun getAllMessages(): Flow<List<Messages>> {
       return messageDao.getAllMessages()
    }

    override   fun getUnsentMessages(): List<Messages> {
        var emptyMessages  = listOf<Messages>()
        emptyMessages  =   messageDao.getUnsetMessages()
        return emptyMessages
    }

    override fun updateMessages(messages: List<Messages>) {
        messageDao.updateMessages(messages)
    }

    override fun updateMessage(messages: Messages) {
        messageDao.updateMessage(messages)
    }

    override fun updateMessageById(messageid: String) {
        messageDao.updateMessageById(messageid)
    }

    override fun deleteMessages() {
     messageDao.deleteMessages()
    }
}