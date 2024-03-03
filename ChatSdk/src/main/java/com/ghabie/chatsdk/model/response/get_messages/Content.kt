package com.ghabie.chatsdk.model.response.get_messages

data class Content(
    val body: String? = "",
    val feTimeStamp: Long? = null,
    val id: String? = null,
    val msgRead: Boolean? = null,
    val msgType: String? = null,
    val orgId: String? = null,
    val recipientId: String? = null,
    val senderId: String? = null
)