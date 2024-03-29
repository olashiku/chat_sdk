package com.ghabie.chatsdk.storage.room.datasource

import com.ghabie.chatsdk.model.response.connection.ConnectionData
import com.ghabie.chatsdk.storage.room.dao.ConnectionDao
import kotlinx.coroutines.flow.Flow

interface ConnectionDataSource {
    fun getConnections(): Flow<List<ConnectionData>>
    fun saveConnections(connectionData: List<ConnectionData>)
}
 class ConnectionDataSourceImpl(val connectionDao: ConnectionDao):ConnectionDataSource{

     override fun getConnections(): Flow<List<ConnectionData>> {
     return connectionDao.getConnectionData()
     }

     override fun saveConnections(connectionData: List<ConnectionData>) {
         println("expecting_connection_data $connectionData")
         connectionDao.updateConnection(connectionData)
     }

 }