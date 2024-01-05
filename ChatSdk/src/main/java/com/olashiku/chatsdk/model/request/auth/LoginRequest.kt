package com.olashiku.chatsdk.model.request.auth

data class LoginRequest(
    val action: String,
    val msgId: String,
    val msgLocation: String,
    val msgPlatform: String,
    val msgSender: MsgSender,
    val msgTimeStamp: String
)