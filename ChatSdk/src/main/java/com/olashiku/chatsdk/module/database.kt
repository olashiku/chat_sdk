package com.olashiku.chatsdk.module

import androidx.room.Room
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.room.datasource.MessageDataSource
import com.olashiku.chatsdk.storage.room.datasource.MessageDataSourceImpl
import com.olashiku.chatsdk.storage.room.db.ChatDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storage =module{
    single { PaperPrefs(get()) }
    single {  Room.databaseBuilder(androidApplication(), ChatDatabase::class.java, "ChatDatabase").fallbackToDestructiveMigration().build() }
    single(createdAtStart = false){get<ChatDatabase>().messageDao()}

    /** data sources*/
    single <MessageDataSource>{MessageDataSourceImpl(messageDao = get())}

}