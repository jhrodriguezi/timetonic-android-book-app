package com.timetonic.booklistapp.ui.book

import com.timetonic.booklistapp.data.local.model.BookUi

data class BookListUiState(
    val isError: Boolean = false,
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val books: List<BookUi> = emptyList()
)