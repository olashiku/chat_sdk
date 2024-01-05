package com.olashiku.chatsdk.model.request.typing

data class MsgSender(
    val deviceId: String,
    val orgId: String,
    val userId: String
)