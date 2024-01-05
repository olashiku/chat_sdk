package com.olashiku.chatsdk.views.fragment.message.model

import com.olashiku.chatsdk.R

data class MessageModel(
    val image:Int,
    val message:String,
    val recipient:String,
    val timeStamp:String
)

 fun getMessages():List<MessageModel>{
     return listOf(
         MessageModel(R.drawable.ic_avatar,"Hi there. I’m Bothrs Assistant. How can I help you?","Customer Service","1d ago"),
         MessageModel(R.drawable.ic_avatar,"Hi there. I’m Bothrs Assistant. How can I help you?","Customer Service","1d ago"),
         MessageModel(R.drawable.ic_avatar,"Hi there. I’m Bothrs Assistant. How can I help you?","Customer Service","1d ago"),
         MessageModel(R.drawable.ic_avatar,"Hi there. I’m Bothrs Assistant. How can I help you?","Customer Service","1d ago"),
         MessageModel(R.drawable.ic_avatar,"Hi there. I’m Bothrs Assistant. How can I help you?","Customer Service","1d ago"),
         )

 }