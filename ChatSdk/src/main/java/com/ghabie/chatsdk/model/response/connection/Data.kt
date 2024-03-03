package com.ghabie.chatsdk.model.response.connection

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

data class Data(
    val agentId: String,
    val connectionId: String,
    val connectionName: String,
    val connectionStatus: String,
    val message: List<Message>,
    val orgId: String,
    val pairedTimeStamp: Int,
    val userId: String
)

@Entity(tableName = "ConnectionData")
data class ConnectionData(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val agentId: String,
    val connectionId: String,
    val connectionName: String,
    val connectionStatus: String,
    val message: String,
    val orgId: String,
    val pairedTimeStamp: Int,
    val userId: String
)

 fun Data.convertConnection():ConnectionData{
     return ConnectionData(
         id = 0,
         agentId = this.agentId,
         connectionId = this.connectionId,
         connectionName = this.connectionName,
         connectionStatus = this.connectionStatus,
         message =Gson().toJson(this.message),
         orgId = this.orgId,
         pairedTimeStamp = this.pairedTimeStamp,
         userId = this.userId
     )
 }