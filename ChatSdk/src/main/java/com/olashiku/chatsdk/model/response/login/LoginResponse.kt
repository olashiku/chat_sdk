package com.olashiku.chatsdk.model.response.login

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "data")
    val `data`: Data? = null,
    @Json(name = "status")
    val status: String ? = null,
    @Json(name = "type")
    val type: String ? = null
)