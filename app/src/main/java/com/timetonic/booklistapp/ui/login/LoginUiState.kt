package com.timetonic.booklistapp.ui.login

import androidx.annotation.StringRes

/**
 * Represents the UI state for the login screen.
 * @property isError Indicates if an error occurred during the login process.
 * @property isSuccessful Indicates if the login process was successful.
 * @property isLoading Indicates if the login process is in progress.
 * @property errorMessage The error message, if any.
 * @property errorFormEmail The error message resource ID for the email field, if any. Do it this way to avoid
 *                          suspend functions in the view model.
 * @property errorFormPassword The error message resource ID for the password field, if any. Do it this way to avoid
 *                          suspend functions in the view model.
 */
data class LoginUiState(
    val isError: Boolean = false,
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    @StringRes val errorFormEmail: Int? = null,
    @StringRes val errorFormPassword: Int? = null
)