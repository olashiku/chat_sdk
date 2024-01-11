package com.olashiku.chatsdk.model.response.connection

data class Message(
    val body: String,
    val feTimeStamp: Int,
    val id: String,
    val msgRead: Boolean,
    val msgType: String,
    val recipientId: String,
    val senderId: String
)