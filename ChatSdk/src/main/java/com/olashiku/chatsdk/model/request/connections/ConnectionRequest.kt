package com.olashiku.chatsdk.model.request.connections

data class ConnectionRequest(
    val action: String,
    val `data`: Data,
    val msgId: String
)