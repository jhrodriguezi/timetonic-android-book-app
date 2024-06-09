package com.timetonic.booklistapp.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the response from the server for OAuth key retrieval.
 * @property status The status of the response.
 * @property errorCode The error code, if status is not "success".
 * @property errorMsg The error message, if status is not "success".
 * @property oauthKey The OAuth key, if status is "success".
 * @property oauthUserId The OAuth user ID, if status is "success".
 */
data class OauthKeyResponse(
    @SerializedName("status") val status: String,
    @SerializedName("errorCode") val errorCode: String?,
    @SerializedName("errorMsg") val errorMsg: String?,
    @SerializedName("oauthkey") val oauthKey: String?,
    @SerializedName("o_u") val oauthUserId: String?
)