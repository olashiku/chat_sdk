package com.olashiku.chatsdk.model.response.get_messages

data class Pageable(
    val offset: Int? = null,
    val pageNumber: Int? = null,
    val pageSize: Int? = null,
    val paged: Boolean? = null,
    val sort: SortX? = null,
    val unpaged: Boolean? = null
)