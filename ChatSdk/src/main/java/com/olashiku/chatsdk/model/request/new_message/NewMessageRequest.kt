package com.olashiku.chatsdk.model.request.new_message

data class NewMessageRequest(
    val action: String,
    val message: Message,
    val msgId: String,
    val msgPlatform: String,
    val msgReceiver: MsgReceiver? = null,
    val msgSender: MsgSender,
    val msgTimeStamp: Long
)