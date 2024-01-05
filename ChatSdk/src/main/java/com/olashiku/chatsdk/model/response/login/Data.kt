package com.olashiku.chatsdk.model.response.login

import com.squareup.moshi.Json

data class Data(
    @Json(name = "connection")
    val connection: Connection? = null,
    @Json(name = "orgid")
    val orgid: String? = null,
    @Json(name = "useruuid")
    val useruuid: String? = null,
    @Json(name = "sessionid")
    val sessionid: String? = null,
    @Json(name = "userStatus")
    val userStatus: String? = null,
    @Json(name = "userid")
    val userid: String? = null,
    @Json(name = "websocketaccountstatus")
    val websocketaccountstatus: String? = null,
    @Json(name = "msgSenderId")
    val msgSenderId: String? = null
)