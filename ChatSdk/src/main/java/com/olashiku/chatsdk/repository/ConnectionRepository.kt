package com.olashiku.chatsdk.repository

import com.olashiku.chatsdk.model.response.connection.ConnectionData
import com.olashiku.chatsdk.model.response.connection.ConnectionResponse
import com.olashiku.chatsdk.model.response.connection.convertConnection
import com.olashiku.chatsdk.storage.room.datasource.ConnectionDataSource
import com.olashiku.chatsdkandroid.utils.getObject
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