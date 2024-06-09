package com.timetonic.booklistapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.timetonic.booklistapp.R
import com.timetonic.booklistapp.data.local.repository.TimetonicRepository
import com.timetonic.booklistapp.data.remote.model.LogInParams
import com.timetonic.booklistapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val timetonicRepository: TimetonicRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> updateEmail(event)
            is LoginUiEvent.PasswordChanged -> updatePassword(event)
            is LoginUiEvent.CloseBannerErrorMessage -> closeBanner()
            is LoginUiEvent.Submit -> submit()
        }
    }

    private fun closeBanner() {
        _loginState.update {
            it.copy(
                isError = false,
            )
        }
    }

    private fun submit() {
        if (_loginState.value.isLoading) return
        if (!verifyFields()) return

        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true) }

            val logInParams = LogInParams(
                login = email,
                password = password
            )
            when (val result = timetonicRepository.logIn(logInParams)) {
                is Result.Success -> {
                    _loginState.update {
                        it.copy(
                            isSuccessful = true,
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    _loginState.update {
                        it.copy(
                            isError = true,
                            errorMessage = result.throwable.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun updateEmail(event: LoginUiEvent.EmailChanged) {
        email = event.inputValue
        _loginState.update {
            it.copy(
                errorFormEmail = null
            )
        }
    }

    private fun updatePassword(event: LoginUiEvent.PasswordChanged) {
        password = event.inputValue
        _loginState.update {
            it.copy(
                errorFormPassword = null
            )
        }
    }

    private fun verifyFields(): Boolean {
        val isEmailValid = verifyEmail()
        val isPasswordValid = verifyPassword()
        return isEmailValid && isPasswordValid
    }

    private fun verifyPassword(): Boolean {
        if (password.isBlank() || password.isEmpty()) {
            _loginState.update {
                it.copy(
                    errorFormPassword = R.string.login_error_msg_empty_password
                )
            }
            return false
        }

        _loginState.update {
            it.copy(
                errorFormPassword = null
            )
        }
        return true
    }

    private fun verifyEmail(): Boolean {
        if (email.isBlank() || email.isEmpty()) {
            _loginState.update {
                it.copy(
                    errorFormEmail = R.string.login_error_msg_empty_email
                )
            }
            return false
        }

        if (!isValidEmail(email)) {
            _loginState.update {
                it.copy(
                    errorFormEmail = R.string.login_error_msg_not_match_email_pattern
                )
            }
            return false
        }

        _loginState.update {
            it.copy(
                errorFormEmail = null
            )
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val repository: TimetonicRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}