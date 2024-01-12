package com.olashiku.chatsdk.model.response.online_offline

data class OnlineOfflineStatusResponse(
    val `data`: Data,
    val status: Boolean,
    val type: String
)