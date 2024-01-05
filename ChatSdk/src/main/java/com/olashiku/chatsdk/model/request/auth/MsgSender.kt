package com.olashiku.chatsdk.model.request.auth

data class MsgSender(
    val msgSenderDeviceId: String,
    val orgId: String,
    val username: String
)