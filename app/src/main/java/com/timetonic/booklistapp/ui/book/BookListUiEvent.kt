package com.timetonic.booklistapp.ui.book

/**
 * Sealed class representing events for the Book List UI.
 */
sealed class BookListUiEvent {

    /**
     * Event indicating the request to load books.
     */
    object OnLoadBooks : BookListUiEvent()

    /**
     * Event indicating the request to close the banner error message.
     */
    object CloseBannerErrorMessage : BookListUiEvent()
}