package com.timetonic.booklistapp.data.remote.model

data class GetAllBooksParams(
    val serverStamp: String? = null,
    val bookCode: String? = null,
    val bookOwner: String? = null
)
