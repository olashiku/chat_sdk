package com.ghabie.chatsdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ghabie.chatsdk.model.response.connection.ConnectionData
import com.ghabie.chatsdk.repository.ConnectionRepository

class ConnectionViewModel(private val connectionRepository: ConnectionRepository):BaseViewModel() {

    fun getConnectionDetails():LiveData<List<ConnectionData>>{
       return connectionRepository.getConnectionFromSource().asLiveData()
    }
}