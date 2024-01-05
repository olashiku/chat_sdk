package com.olashiku.chatsdk.storage.room.datasource

import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.storage.room.dao.MessageDao
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
   fun getAllMessages(): Flow<List<Messages>>
   fun updateMessages(messages:List<Messages>)
    fun updateMessage(messages:Messages)
    fun updateMessageById(messageid:String)
}

class MessageDataSourceImpl(val messageDao:MessageDao):MessageDataSource{

    override fun getAllMessages(): Flow<List<Messages>> {
       return messageDao.getAllMessages()
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
}