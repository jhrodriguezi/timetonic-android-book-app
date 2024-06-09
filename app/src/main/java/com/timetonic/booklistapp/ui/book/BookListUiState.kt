package com.timetonic.booklistapp.ui.book

import com.timetonic.booklistapp.data.local.model.BookUi

/**
 * Represents the state of the Book List UI.
 * @param isError Indicates if an error occurred.
 * @param isSuccessful Indicates if the operation was successful.
 * @param isLoading Indicates if the UI is in a loading state.
 * @param errorMessage The error message, if any.
 * @param books The list of books to display.
 */
data class BookListUiState(
    val isError: Boolean = false,
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val books: List<BookUi> = emptyList()
)