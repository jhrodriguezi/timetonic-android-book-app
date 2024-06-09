package com.timetonic.booklistapp.data.remote

import com.timetonic.booklistapp.data.remote.model.AllBooksResponse
import com.timetonic.booklistapp.data.remote.model.AppKeyResponse
import com.timetonic.booklistapp.data.remote.model.OauthKeyResponse
import com.timetonic.booklistapp.data.remote.model.SessKeyResponse
import com.timetonic.booklistapp.util.Result
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Enum representing status values.
 * @property value The string value of the status.
 */
enum class Status(val value: String) {
    OK("ok"),
    NOK("nok")
}

/**
 * Interface for interacting with the Timetonic API.
 */
interface TimetonicApi {

    @POST("live/api.php")
    suspend fun createAppKey(
        @Query("req") req: String = "createAppkey",
        @Query("appname") appname: String,
        @Query("version") version: String
    ): Result<AppKeyResponse>

    @POST("live/api.php")
    suspend fun createOauthKey(
        @Query("req") req: String = "createOauthkey",
        @Query("version") version: String,
        @Query("login") login: String,
        @Query("pwd") password: String,
        @Query("appkey") appkey: String
    ): Result<OauthKeyResponse>

    @POST("live/api.php")
    suspend fun createSessKey(
        @Query("req") req: String = "createSesskey",
        @Query("version") version: String,
        @Query("o_u") oauthUserId: String,
        @Query("u_c") userId: String,
        @Query("oauthkey") oauthkey: String
    ): Result<SessKeyResponse>

    @POST("live/api.php")
    suspend fun getAllBooks(
        @Query("req") req: String = "getAllBooks",
        @Query("version") version: String,
        @Query("o_u") oauthUserId: String,
        @Query("u_c") userId: String,
        @Query("sesskey") sesskey: String,
        @Query("sstamp") serverStamp: String?,
        @Query("b_c") bookCode: String?,
        @Query("b_o") bookOwner: String?
    ): Result<AllBooksResponse>
}