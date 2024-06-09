package com.timetonic.booklistapp.data.remote

import com.timetonic.booklistapp.data.local.model.BookUi
import com.timetonic.booklistapp.data.local.repository.TimetonicRepository
import com.timetonic.booklistapp.data.remote.model.GetAllBooksParams
import com.timetonic.booklistapp.data.remote.model.LogInParams
import com.timetonic.booklistapp.data.remote.model.SessKeyResponse
import com.timetonic.booklistapp.data.remote.model.toBookUi
import com.timetonic.booklistapp.util.Result
import com.timetonic.booklistapp.util.RetrofitHelper
import com.timetonic.booklistapp.util.SessionManager
import kotlinx.coroutines.flow.first

/**
 * Repository for interacting with the Timetonic API.
 * @property timetonicApi The Timetonic API service.
 * @property sessionManager The session manager for managing user sessions.
 */
class TimetonicApiRepository(
    private val timetonicApi: TimetonicApi = RetrofitHelper.getTimetonicService(),
    private val sessionManager: SessionManager
) : TimetonicRepository {

    companion object {
        /**
         * The API version used for Timetonic requests.
         */
        private val API_VERSION: String
            get() = "6.49q/6.49"
    }

    /**
     * Logs in the user and retrieves a session key.
     * It goes through the following steps:
     * 1. Creates an application key.
     * 2. Creates an OAuth key.
     * 3. Creates a session key.
     * @param dataRequest The login parameters.
     * @return Result containing the session key response.
     */
    override suspend fun logIn(dataRequest: LogInParams): Result<SessKeyResponse> {
        when (val appKeyResult = timetonicApi.createAppKey(
            appname = "android",
            version = API_VERSION
        )) {
            is Result.Success -> {
                if (appKeyResult.data.status == Status.NOK.value) return Result.Error(
                    IllegalStateException(
                        "${appKeyResult.data.errorCode} ${appKeyResult.data.errorMsg}"
                    )
                )
                appKeyResult.data.appKey?.let { sessionManager.saveAppKey(it) }
            }

            is Result.Error -> {
                return Result.Error(appKeyResult.throwable)
            }
        }

        when (val oauthKeyResult = timetonicApi.createOauthKey(
            version = API_VERSION,
            login = dataRequest.login,
            password = dataRequest.password,
            appkey = sessionManager.appKey.first()
        )) {
            is Result.Success -> {
                if (oauthKeyResult.data.status == Status.NOK.value) return Result.Error(
                    IllegalStateException(
                        "${oauthKeyResult.data.errorCode} ${oauthKeyResult.data.errorMsg}"
                    )
                )
                oauthKeyResult.data.oauthKey?.let { sessionManager.saveOauthKey(it) }
                oauthKeyResult.data.oauthUserId?.let { sessionManager.saveOauthUserId(it) }
            }

            is Result.Error -> {
                return Result.Error(oauthKeyResult.throwable)
            }
        }

        val oauthUserId = sessionManager.oauthUserId.first()
        when (val sessKeyResult = timetonicApi.createSessKey(
            version = API_VERSION,
            oauthUserId = oauthUserId,
            userId = oauthUserId,
            oauthkey = sessionManager.oauthKey.first()
        )) {
            is Result.Success -> {
                if (sessKeyResult.data.status == Status.NOK.value) return Result.Error(
                    IllegalStateException(
                        "${sessKeyResult.data.errorCode} ${sessKeyResult.data.errorMsg}"
                    )
                )
                sessKeyResult.data.sessKey?.let { sessionManager.saveSessKey(it) }
                return Result.Success(sessKeyResult.data)
            }

            is Result.Error -> {
                return Result.Error(sessKeyResult.throwable)
            }
        }
    }

    override suspend fun getAllBooks(dataRequest: GetAllBooksParams): Result<List<BookUi>> {
        return when (val allBooksResult = timetonicApi.getAllBooks(
            version = API_VERSION,
            oauthUserId = sessionManager.oauthUserId.first(),
            userId = sessionManager.oauthUserId.first(),
            sesskey = sessionManager.sessKey.first(),
            serverStamp = dataRequest.serverStamp,
            bookOwner = dataRequest.bookOwner,
            bookCode = dataRequest.bookCode
        )) {
            is Result.Success -> {
                if (allBooksResult.data.status == Status.NOK.value) return Result.Error(
                    IllegalStateException(
                        "${allBooksResult.data.errorCode} ${allBooksResult.data.errorMsg}"
                    )
                )
                return allBooksResult.data.allBooks?.books?.let { Result.Success(it.map { it.toBookUi() }) }
                    ?: Result.Success(
                        emptyList()
                    )
            }

            is Result.Error -> {
                Result.Error(allBooksResult.throwable)
            }
        }
    }
}