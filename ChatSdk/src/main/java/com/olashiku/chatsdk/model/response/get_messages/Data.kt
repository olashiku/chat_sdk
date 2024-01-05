package com.olashiku.chatsdk.model.response.get_messages

data class Data(
    val content: List<Content>? = null,
    val empty: Boolean? = null,
    val first: Boolean? = null,
    val last: Boolean? = null,
    val number: Int? = null,
    val numberOfElements: Int? = null,
    val pageable: Pageable? = null,
    val size: Int? = null,
    val sort: SortX? = null,
    val totalElements: Int? = null,
    val totalPages: Int? = null
)