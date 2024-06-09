package com.timetonic.booklistapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class SessKeyResponse(
    @SerializedName("status") val status: String,
    @SerializedName("errorCode") val errorCode: String?,
    @SerializedName("errorMsg") val errorMsg: String?,
    @SerializedName("sesskey") val sessKey: String?,
)