package com.ghabie.chatsdk.repository

import com.ghabie.chatsdk.model.response.connection.ConnectionData
import com.ghabie.chatsdk.model.response.connection.ConnectionResponse
import com.ghabie.chatsdk.model.response.connection.convertConnection
import com.ghabie.chatsdk.storage.room.datasource.ConnectionDataSource
import com.ghabie.chatsdkandroid.utils.getObject
import kotlinx.coroutines.flow.Flow

interface ConnectionRepository {
    fun saveConnection(connectionResponse:String)
    fun getConnectionFromSource(): Flow<List<ConnectionData>>
}

 class ConnectionRepositoryImpl(private val connectionDataSource: ConnectionDataSource):ConnectionRepository{

     override fun saveConnection(connectionResponse: String) {
         val response = connectionResponse.getObject<ConnectionResponse>()
        val convertedResponse =  response.data.map { it.convertConnection() }
         connectionDataSource.saveConnections(convertedResponse)
     }

     override fun getConnectionFromSource(): Flow<List<ConnectionData>> {
        return connectionDataSource.getConnections()
     }

 }