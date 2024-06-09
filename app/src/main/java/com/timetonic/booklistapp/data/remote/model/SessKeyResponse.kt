package com.timetonic.booklistapp.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the response from the server for session key retrieval.
 * @property status The status of the response.
 * @property errorCode The error code, if status is not "success".
 * @property errorMsg The error message, if status is not "success".
 * @property sessKey The session key, if status is "success".
 */
data class SessKeyResponse(
    @SerializedName("status") val status: String,
    @SerializedName("errorCode") val errorCode: String?,
    @SerializedName("errorMsg") val errorMsg: String?,
    @SerializedName("sesskey") val sessKey: String?,
)