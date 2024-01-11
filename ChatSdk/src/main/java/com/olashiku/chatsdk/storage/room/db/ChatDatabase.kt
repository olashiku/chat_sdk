package com.olashiku.chatsdk.storage.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olashiku.chatsdk.model.response.connection.ConnectionData
import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.storage.room.dao.ConnectionDao
import com.olashiku.chatsdk.storage.room.dao.MessageDao

@Database(entities = [Messages::class,ConnectionData::class], version = 8, exportSchema = false)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun messageDao() : MessageDao
    abstract fun connectionDao(): ConnectionDao

}