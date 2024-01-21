package com.olashiku.chatsdk.views.fragment.message.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.olashiku.chatsdk.R
import com.olashiku.chatsdk.model.response.connection.Message

data class MessageModel(
    val image:Int,
    val message:String,
    val recipient:String,
    val timeStamp:String
)
data class MessageListing(
    val connectionName:String,
    val agentId:String,
    val content:String,
    val profileImage:String,
    val timeStamp:Int

)



fun jsonToList(jsonArray: String): List<Message> {
    val gson = Gson()
    val messageType = object : TypeToken<List<Message>>() {}.type
    return gson.fromJson(jsonArray, messageType)
}