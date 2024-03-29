package com.ghabie.chatsdk.storage.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ghabie.chatsdk.model.response.connection.ConnectionData
import kotlinx.coroutines.flow.Flow

@Dao
interface ConnectionDao:BaseDao<ConnectionData> {

    @Query("select * from ConnectionData")
    fun getConnectionData(): Flow<List<ConnectionData>>

    @Query("Delete from ConnectionData")
    fun deleteConnection()

    @Transaction
    fun updateConnection(connection:List<ConnectionData>){
        deleteConnection()
        insertAll(connection)
    }
}