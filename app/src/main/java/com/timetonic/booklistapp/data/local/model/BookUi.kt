package com.timetonic.booklistapp.data.local.model

/**
 * Represents a book in the UI.
 * @param title The title of the book.
 * @param imageUrl The URL of the book's image.
 */
data class BookUi(
    val title: String,
    val imageUrl: String?
)