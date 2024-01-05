package com.olashiku.chatsdk.model.response.message
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Messages")
data class Messages(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val msgId:String,
    val timeStamp:Int,
    val senderUserId:String,
    val receiverUserId:String,
    val type:String,
    val msgRead:Boolean,
    val content:String,
    val sendBy:String,
    val messageStatus:String
)