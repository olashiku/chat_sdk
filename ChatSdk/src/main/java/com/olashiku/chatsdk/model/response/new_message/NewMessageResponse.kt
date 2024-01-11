package com.olashiku.chatsdk.model.response.new_message

import com.squareup.moshi.Json

data class NewMessageResponse(
    @Json(name = "action")
    val action: String? = null,
    @Json(name = "type")
    val type:String? = null,
    @Json(name = "message")
    val message: Message? = null,
    @Json(name = "msgId")
    val msgId: String? = null,
    @Json(name = "msgPlatform")
    val msgPlatform: String? = null,
    @Json(name = "msgSender")
    val msgSender: MsgSender? = null,
    @Json(name = "msgTimeStamp")
    val msgTimeStamp: Long? = null
)