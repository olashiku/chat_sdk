package com.ghabie.chatsdk.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ghabie.chatsdk.model.response.message.Messages
import com.ghabie.chatsdk.model.response.online_offline.OnlineOfflineStatusResponse
import com.ghabie.chatsdk.model.response.typing.TypingResponse
import com.ghabie.chatsdk.repository.MessageRepository

@SuppressLint("CheckResult")
class MessageViewModel(val messageRepository: MessageRepository) :BaseViewModel() {

    fun getMessagesFromDatabase(): LiveData<List<Messages>> {
    return messageRepository.getMessagesFromDatabase().asLiveData()
    }

     fun getTypingMessageStatus():LiveData<TypingResponse>{
         return messageRepository.getTypingMessageMutableLiveData()
     }


     fun getOnlineOfflineStatus():LiveData<OnlineOfflineStatusResponse>{
         return  messageRepository.getOnlineOfflineStatus()
     }




}