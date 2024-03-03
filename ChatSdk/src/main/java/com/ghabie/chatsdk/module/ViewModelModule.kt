package com.ghabie.chatsdk.module

import com.ghabie.chatsdk.viewmodel.AgentViewModel
import com.ghabie.chatsdk.viewmodel.AuthenticationViewModel
import com.ghabie.chatsdk.viewmodel.ConnectionViewModel
import com.ghabie.chatsdk.viewmodel.MessageViewModel
import com.ghabie.chatsdk.viewmodel.SocketViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel { AuthenticationViewModel(authRepository = get()) }
    viewModel { MessageViewModel(messageRepository = get()) }
    viewModel { SocketViewModel(socketRepository = get()) }
    viewModel { ConnectionViewModel(connectionRepository = get()) }
    viewModel { AgentViewModel(agentRepository = get()) }
}