package com.olashiku.chatsdk.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.olashiku.chatsdk.model.Constants
import com.olashiku.chatsdk.model.NetworkActions
import com.olashiku.chatsdk.model.request.get_messages.GetMessagesRequest
import com.olashiku.chatsdk.model.request.get_messages.Query
import com.olashiku.chatsdk.model.request.new_message.Message
import com.olashiku.chatsdk.model.request.new_message.MsgReceiver
import com.olashiku.chatsdk.model.request.new_message.MsgSender
import com.olashiku.chatsdk.model.request.new_message.NewMessageRequest
import com.olashiku.chatsdk.model.request.typing.TypingRequest
import com.olashiku.chatsdk.model.response.login.Connection
import com.olashiku.chatsdk.model.response.message.Messages
import com.olashiku.chatsdk.model.response.typing.TypingResponse
import com.olashiku.chatsdk.repository.MessageRepository
import com.olashiku.chatsdk.storage.PaperPrefs
import com.olashiku.chatsdk.storage.getAnyPref
import com.olashiku.chatsdk.storage.getStringPref
import com.olashiku.chatsdkandroid.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

@SuppressLint("CheckResult")
class MessageViewModel(val messageRepository: MessageRepository) :BaseViewModel() {

    fun getMessagesFromDatabase(): LiveData<List<Messages>> {
    return messageRepository.getMessagesFromDatabase().asLiveData()
    }

     fun getTypingMessageStatus():LiveData<TypingResponse>{
         return messageRepository.getTypingMessageFlow().asLiveData()
     }




}