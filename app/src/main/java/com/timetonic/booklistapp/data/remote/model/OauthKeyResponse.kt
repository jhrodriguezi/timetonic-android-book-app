package com.timetonic.booklistapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class OauthKeyResponse(
    @SerializedName("status") val status: String,
    @SerializedName("errorCode") val errorCode: String?,
    @SerializedName("errorMsg") val errorMsg: String?,
    @SerializedName("oauthkey") val oauthKey: String?,
    @SerializedName("o_u") val oauthUserId: String?
)