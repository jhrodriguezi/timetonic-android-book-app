package com.timetonic.booklistapp.ui.login

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timetonic.booklistapp.R
import com.timetonic.booklistapp.data.remote.TimetonicApiRepository
import com.timetonic.booklistapp.ui.common.BannerErrorMessage
import com.timetonic.booklistapp.ui.common.EmailTextField
import com.timetonic.booklistapp.ui.common.NormalButton
import com.timetonic.booklistapp.ui.common.PasswordTextField
import com.timetonic.booklistapp.ui.common.TitleText
import com.timetonic.booklistapp.ui.theme.BookListAppTheme
import com.timetonic.booklistapp.util.SessionDataStore

/**
 * Composable function for the login screen.
 * @param onNavigateToAuthenticatedRoute Callback function to navigate to the authenticated route.
 * @param timetonicApiRepository The repository for accessing Timetonic API methods.
 * @param viewModel The view model for handling login functionality.
 */
@Composable
fun LoginScreen(
    onNavigateToAuthenticatedRoute: () -> Unit = {},
    timetonicApiRepository: TimetonicApiRepository = TimetonicApiRepository(
        sessionManager = SessionDataStore(LocalContext.current)
    ),
    viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(timetonicApiRepository))
) {
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(key1 = loginState.isSuccessful) {
        if (loginState.isSuccessful) onNavigateToAuthenticatedRoute.invoke()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(BookListAppTheme.colorScheme.background)
    ) {
        if (!(loginState.isLoading || loginState.isSuccessful)) Column {
            BannerErrorMessage(visibility = loginState.isError,
                message = loginState.errorMessage,
                onClose = { viewModel.onEvent(LoginUiEvent.CloseBannerErrorMessage) })
            Spacer(modifier = Modifier.size(BookListAppTheme.dimens.paddingLarge))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = BookListAppTheme.dimens.paddingLarge),
                contentAlignment = Alignment.Center
            ) {
                LoginCard(email = viewModel.email,
                    errorFormEmail = loginState.errorFormEmail?.let { stringResource(it) },
                    onEmailChange = {
                        viewModel.onEvent(
                            LoginUiEvent.EmailChanged(
                                it
                            )
                        )
                    },
                    password = viewModel.password,
                    errorFormPassword = loginState.errorFormPassword?.let { stringResource(it) },
                    onPasswordChange = {
                        viewModel.onEvent(
                            LoginUiEvent.PasswordChanged(
                                it
                            )
                        )
                    },
                    onSubmit = { viewModel.onEvent(LoginUiEvent.Submit) })
            }
        }
        else CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

/**
 * Composable function for the login card.
 * @param email The email entered by the user.
 * @param errorFormEmail The error message for the email field, if any.
 * @param onEmailChange Callback function for handling changes to the email field.
 * @param password The password entered by the user.
 * @param errorFormPassword The error message for the password field, if any.
 * @param onPasswordChange Callback function for handling changes to the password field.
 * @param onSubmit Callback function for submitting the login form.
 */
@Composable
fun LoginCard(
    email: String,
    errorFormEmail: String?,
    onEmailChange: (String) -> Unit,
    password: String,
    errorFormPassword: String?,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BookListAppTheme.dimens.paddingLarge)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = BookListAppTheme.dimens.paddingLarge)
                .padding(bottom = BookListAppTheme.dimens.paddingExtraLarge)
        ) {
            Image(
                painter = painterResource(R.drawable.timetonic_logo),
                contentDescription = "Company Logo",
                colorFilter = ColorFilter.tint(BookListAppTheme.colorScheme.onSurface),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(BookListAppTheme.dimens.paddingLarge))
            TitleText(
                modifier = Modifier.padding(top = BookListAppTheme.dimens.paddingLarge),
                text = stringResource(id = R.string.login_heading_text)
            )
            Column(modifier = Modifier.fillMaxWidth()) {

                EmailTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = BookListAppTheme.dimens.paddingLarge),
                    value = email,
                    onValueChange = onEmailChange,
                    label = stringResource(id = R.string.login_email),
                    isError = errorFormEmail != null,
                    errorText = errorFormEmail ?: "")

                PasswordTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = BookListAppTheme.dimens.paddingLarge),
                    value = password,
                    onValueChange = onPasswordChange,
                    label = stringResource(id = R.string.login_password_label),
                    isError = errorFormPassword != null,
                    errorText = errorFormPassword ?: "",
                    imeAction = ImeAction.Done)

                NormalButton(
                    modifier = Modifier.padding(top = BookListAppTheme.dimens.paddingLarge),
                    text = stringResource(id = R.string.login_button_text),
                    onClick = onSubmit
                )
            }
        }
    }
}