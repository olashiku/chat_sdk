package com.ghabie.chatsdk.model.response.base_response

data class BaseResponse(
    val `data`: Any?,
    val status: String,
    val type: String
)