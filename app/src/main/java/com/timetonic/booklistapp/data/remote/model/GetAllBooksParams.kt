package com.timetonic.booklistapp.data.remote.model

/**
 * Parameters for retrieving all books from the Timetonic API.
 * @property serverStamp Server timestamp.
 * @property bookCode Book code.
 * @property bookOwner Book owner.
 */
data class GetAllBooksParams(
    val serverStamp: String? = null,
    val bookCode: String? = null,
    val bookOwner: String? = null
)
