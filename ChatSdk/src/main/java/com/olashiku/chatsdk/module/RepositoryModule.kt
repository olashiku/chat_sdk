package com.olashiku.chatsdk.module

import com.olashiku.chatsdk.repository.AuthRepository
import com.olashiku.chatsdk.repository.AuthRepositoryImpl
import com.olashiku.chatsdk.repository.MessageRepository
import com.olashiku.chatsdk.repository.MessageRepositoryImpl
import com.olashiku.chatsdk.repository.SocketRepository
import com.olashiku.chatsdk.repository.SocketRepositoryImpl
import org.koin.dsl.module

val repository = module{
    single <AuthRepository>{ AuthRepositoryImpl() }
    single <MessageRepository>{MessageRepositoryImpl( messagesDataSource = get())}
    single <SocketRepository> { SocketRepositoryImpl(socket = get(), authRepository = get(), messageRepository = get()) }
}