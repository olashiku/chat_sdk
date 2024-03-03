package com.ghabie.chatsdk.model.response.login


data class Data(
    val connection: Connection? = null,
    val orgid: String? = null,
    val useruuid: String? = null,
    val sessionid: String? = null,
    val userStatus: String? = null,
    val userid: String? = null,
    val websocketaccountstatus: String? = null,
    val msgSenderId: String? = null
)