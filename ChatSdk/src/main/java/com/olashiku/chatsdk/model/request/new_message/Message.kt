package com.olashiku.chatsdk.model.request.new_message

data class Message(
    val content: String,
    val source: String,
    val type: String
)