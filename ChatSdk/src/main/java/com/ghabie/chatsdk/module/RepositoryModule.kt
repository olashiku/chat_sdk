package com.ghabie.chatsdk.module

import com.ghabie.chatsdk.repository.AgentRepository
import com.ghabie.chatsdk.repository.AgentRepositoryImpl
import com.ghabie.chatsdk.repository.AuthRepository
import com.ghabie.chatsdk.repository.AuthRepositoryImpl
import com.ghabie.chatsdk.repository.ConnectionRepository
import com.ghabie.chatsdk.repository.ConnectionRepositoryImpl
import com.ghabie.chatsdk.repository.MessageRepository
import com.ghabie.chatsdk.repository.MessageRepositoryImpl
import com.ghabie.chatsdk.repository.SocketRepository
import com.ghabie.chatsdk.repository.SocketRepositoryImpl
import org.koin.dsl.module

val repository = module{
    single <AuthRepository>{ AuthRepositoryImpl() }
    single <MessageRepository>{MessageRepositoryImpl( messagesDataSource = get())}
    single<ConnectionRepository> { ConnectionRepositoryImpl(connectionDataSource = get()) }
    single <SocketRepository> { SocketRepositoryImpl(socket = get(), authRepository = get(), messageRepository = get(),connectionRepository = get()) }
    single <AgentRepository>{ AgentRepositoryImpl(api = get())  }
}