package com.timetonic.booklistapp.data.remote.model

/**
 * Represents the parameters required for logging in.
 * @property login The email used for logging in.
 * @property password The password associated with the account.
 */
data class LogInParams(
    val login: String,
    val password: String
)
