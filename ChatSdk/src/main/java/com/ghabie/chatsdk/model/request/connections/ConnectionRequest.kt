package com.ghabie.chatsdk.model.request.connections

data class ConnectionRequest(
    val action: String,
    val `data`: Data,
    val msgId: String
)