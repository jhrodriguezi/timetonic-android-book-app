package com.timetonic.booklistapp.data.local.repository

import com.timetonic.booklistapp.data.remote.model.LogInParams
import com.timetonic.booklistapp.data.remote.model.SessKeyResponse
import com.timetonic.booklistapp.util.Result

interface TimetonicRepository {
    suspend fun logIn(
        dataRequest: LogInParams
    ): Result<SessKeyResponse>
}