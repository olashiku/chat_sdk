package com.olashiku.chatsdk.storage.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.olashiku.chatsdk.model.response.message.Messages
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao:BaseDao<Messages> {

    @Query("select * from Messages")
    fun getAllMessages(): Flow<List<Messages>>

    @Query("DELETE FROM Messages")
    fun deleteMessages()

    @Query("Update Messages set messageStatus ='READ' where msgId =:messageid")
    fun updateMessageById(messageid:String)

    @Transaction
    fun updateMessages(messages: List<Messages>){
        deleteMessages()
        insertAll(messages)
    }

    fun updateMessage(message:Messages){
        insert(message)
    }
}