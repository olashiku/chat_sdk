package com.olashiku.chatsdk.model.request.get_messages

data class Query(
    val from: String,
    val orgId: String,
    val page: String,
    val pagesize: String,
    val to: String
)