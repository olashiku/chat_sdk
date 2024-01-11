package com.olashiku.chatsdk.module

import com.olashiku.chatsdk.repository.AgentRepository
import com.olashiku.chatsdk.repository.AgentRepositoryImpl
import com.olashiku.chatsdk.repository.AuthRepository
import com.olashiku.chatsdk.repository.AuthRepositoryImpl
import com.olashiku.chatsdk.repository.ConnectionRepository
import com.olashiku.chatsdk.repository.ConnectionRepositoryImpl
import com.olashiku.chatsdk.repository.MessageRepository
import com.olashiku.chatsdk.repository.MessageRepositoryImpl
import com.olashiku.chatsdk.repository.SocketRepository
import com.olashiku.chatsdk.repository.SocketRepositoryImpl
import com.olashiku.chatsdk.storage.room.datasource.ConnectionDataSource
import org.koin.dsl.module

val repository = module{
    single <AuthRepository>{ AuthRepositoryImpl() }
    single <MessageRepository>{MessageRepositoryImpl( messagesDataSource = get())}
    single<ConnectionRepository> { ConnectionRepositoryImpl(connectionDataSource = get()) }
    single <SocketRepository> { SocketRepositoryImpl(socket = get(), authRepository = get(), messageRepository = get(),connectionRepository = get()) }
    single <AgentRepository>{ AgentRepositoryImpl(api = get())  }
}