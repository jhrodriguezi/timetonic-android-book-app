package com.timetonic.booklistapp.ui.book

sealed class BookListUiEvent {
    object onLoadBooks: BookListUiEvent()
    object CloseBannerErrorMessage: BookListUiEvent()
}