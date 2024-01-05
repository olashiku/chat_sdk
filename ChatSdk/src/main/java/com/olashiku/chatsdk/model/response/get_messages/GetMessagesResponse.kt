package com.olashiku.chatsdk.model.response.get_messages

data class GetMessagesResponse(
    val `data`: Data? = null,
    val type: String? = null,
    val status:String? = null
)

data class GetMessagesIntroResponse(
    val type: String? = null,
    val data:List<Any> = emptyList()
)