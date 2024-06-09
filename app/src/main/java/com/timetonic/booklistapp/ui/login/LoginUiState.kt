package com.timetonic.booklistapp.ui.login

import androidx.annotation.StringRes

data class LoginUiState(
    val isError: Boolean = false,
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    @StringRes val errorFormEmail: Int? = null,
    @StringRes val errorFormPassword: Int? = null
)