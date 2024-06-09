package com.timetonic.booklistapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class AppKeyResponse (
    @SerializedName("status")
    val status: String,
    @SerializedName("errorCode")
    val errorCode: String?,
    @SerializedName("errorMsg")
    val errorMsg: String?,
    @SerializedName("appkey")
    val appKey: String?
)