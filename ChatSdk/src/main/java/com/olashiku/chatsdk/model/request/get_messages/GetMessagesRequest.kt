package com.olashiku.chatsdk.model.request.get_messages

data class GetMessagesRequest(
    val action: String,
    val msgId: String,
    val query: Query
)