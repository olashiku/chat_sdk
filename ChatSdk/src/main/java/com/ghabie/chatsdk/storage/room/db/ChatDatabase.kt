package com.ghabie.chatsdk.storage.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ghabie.chatsdk.model.response.connection.ConnectionData
import com.ghabie.chatsdk.model.response.message.Messages
import com.ghabie.chatsdk.storage.room.dao.ConnectionDao
import com.ghabie.chatsdk.storage.room.dao.MessageDao

@Database(entities = [Messages::class,ConnectionData::class], version = 8, exportSchema = false)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun messageDao() : MessageDao
    abstract fun connectionDao(): ConnectionDao

}