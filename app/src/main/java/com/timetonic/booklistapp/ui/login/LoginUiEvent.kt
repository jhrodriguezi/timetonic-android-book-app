package com.timetonic.booklistapp.ui.login

sealed class LoginUiEvent {
    data class EmailChanged(val inputValue: String) : LoginUiEvent()
    data class PasswordChanged(val inputValue: String) : LoginUiEvent()
    data object CloseBannerErrorMessage : LoginUiEvent()
    data object Submit : LoginUiEvent()
}