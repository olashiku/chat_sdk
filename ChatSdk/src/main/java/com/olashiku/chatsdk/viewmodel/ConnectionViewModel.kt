package com.olashiku.chatsdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.olashiku.chatsdk.model.response.connection.ConnectionData
import com.olashiku.chatsdk.repository.ConnectionRepository

class ConnectionViewModel(private val connectionRepository: ConnectionRepository):BaseViewModel() {

    fun getConnectionDetails():LiveData<List<ConnectionData>>{
       return connectionRepository.getConnectionFromSource().asLiveData()
    }
}