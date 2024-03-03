package com.ghabie.chatsdk.model.response.new_message



data class NewMessageResponse(
    val action: String? = null,
    val type:String? = null,
    val message: Message? = null,
    val msgId: String? = null,
    val msgPlatform: String? = null,
    val msgSender: MsgSender? = null,
    val msgTimeStamp: Long? = null
)