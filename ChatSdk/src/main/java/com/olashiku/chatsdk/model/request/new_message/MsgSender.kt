package com.olashiku.chatsdk.model.request.new_message

data class MsgSender(
    val deviceId: String,
    val orgId: String,
    val userId: String
)