package com.olashiku.chatsdk.model.request.typing

data class TypingRequest(
    val action: String,
    val msgId: String,
    val msgPlatform: String,
    val msgReceiver: MsgReceiver,
    val msgSender: MsgSender,
    val msgTimeStamp: String
)