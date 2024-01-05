package com.olashiku.chatsdk.module

import com.olashiku.chatsdk.viewmodel.AuthenticationViewModel
import com.olashiku.chatsdk.viewmodel.MessageViewModel
import com.olashiku.chatsdk.viewmodel.SocketViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel { AuthenticationViewModel(authRepository = get()) }
    viewModel { MessageViewModel(messageRepository = get()) }
    viewModel { SocketViewModel(socketRepository = get()) }
}