package com.ghabie.chatsdk.module

import androidx.room.Room
import com.ghabie.chatsdk.storage.PaperPrefs
import com.ghabie.chatsdk.storage.room.datasource.ConnectionDataSource
import com.ghabie.chatsdk.storage.room.datasource.ConnectionDataSourceImpl
import com.ghabie.chatsdk.storage.room.datasource.MessageDataSource
import com.ghabie.chatsdk.storage.room.datasource.MessageDataSourceImpl
import com.ghabie.chatsdk.storage.room.db.ChatDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storage =module{
    single { PaperPrefs(get()) }
    single {  Room.databaseBuilder(androidApplication(), ChatDatabase::class.java, "ChatDatabase").fallbackToDestructiveMigration().build() }
    single(createdAtStart = false){get<ChatDatabase>().messageDao()}
    single(createdAtStart = false) { get<ChatDatabase>().connectionDao() }

    /** data sources*/
    single <MessageDataSource>{MessageDataSourceImpl(messageDao = get())}
    single<ConnectionDataSource> {ConnectionDataSourceImpl(connectionDao = get())  }
}