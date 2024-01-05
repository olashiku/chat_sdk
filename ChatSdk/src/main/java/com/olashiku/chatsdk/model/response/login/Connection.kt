package com.olashiku.chatsdk.model.response.login

import com.squareup.moshi.Json

data class Connection(
    @Json(name = "agentId")
    val agentId: String? = null,
    @Json(name = "agentUserName")
    val agentUserName: String? = null,
    @Json(name = "connectionStatus")
    val connectionStatus: String? = null,
    @Json(name = "firstName")
    val firstName: String? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "orgId")
    val orgId: String? = null,
    @Json(name = "profileImageUrl")
    val profileImageUrl: String? = null,
    @Json(name = "userId")
    val userId: String ? = null
)