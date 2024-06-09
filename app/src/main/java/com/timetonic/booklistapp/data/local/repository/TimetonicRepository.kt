package com.timetonic.booklistapp.data.local.repository

import com.timetonic.booklistapp.data.local.model.BookUi
import com.timetonic.booklistapp.data.remote.model.GetAllBooksParams
import com.timetonic.booklistapp.data.remote.model.LogInParams
import com.timetonic.booklistapp.data.remote.model.SessKeyResponse
import com.timetonic.booklistapp.util.Result

/**
 * Interface defining methods for interacting with the Timetonic API.
 */
interface TimetonicRepository {

    /**
     * Logs in the user and retrieves a session key.
     * @param dataRequest The login parameters.
     * @return Result containing the session key response.
     */
    suspend fun logIn(
        dataRequest: LogInParams
    ): Result<SessKeyResponse>

    /**
     * Retrieves all books from the Timetonic API.
     * @param dataRequest The parameters for fetching books.
     * @return Result containing a list of books.
     */
    suspend fun getAllBooks(
        dataRequest: GetAllBooksParams
    ): Result<List<BookUi>>
}