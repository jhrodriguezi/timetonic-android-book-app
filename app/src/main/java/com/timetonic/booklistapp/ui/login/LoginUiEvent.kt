package com.timetonic.booklistapp.ui.login

/**
 * Sealed class representing UI events for the login screen.
 */
sealed class LoginUiEvent {

    /**
     * Event indicating that the email input value has changed.
     * @param inputValue The new value of the email input.
     */
    data class EmailChanged(val inputValue: String) : LoginUiEvent()

    /**
     * Event indicating that the password input value has changed.
     * @param inputValue The new value of the password input.
     */
    data class PasswordChanged(val inputValue: String) : LoginUiEvent()

    /**
     * Event indicating that the banner displaying error messages should be closed.
     */
    data object CloseBannerErrorMessage : LoginUiEvent()

    /**
     * Event indicating that the login form has been submitted.
     */
    data object Submit : LoginUiEvent()
}