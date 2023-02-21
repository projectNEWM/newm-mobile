package io.newm.feature.login.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.Password
import io.newm.feature.login.screen.password.PasswordState
import io.newm.core.resources.R
import io.newm.core.ui.buttons.SecondaryButton

@Composable
fun CreateAccountScreen(
    onUserLoggedIn: () -> Unit,
    onNext: () -> Unit,
    viewModel: CreateAccountViewModel
) {
    val userState by viewModel.state.collectAsState()

    CreateAccountScreen(
        onUserLoggedIn = onUserLoggedIn,
        onNext = onNext,
        userState = userState,
        setUserEmail = viewModel::setUserEmail,
        setUserPassword = viewModel::setUserPassword,
        setUserPasswordConfirmation = viewModel::setUserPasswordConfirmation,
        requestCode = viewModel::requestCode,
    )
}

@Composable
internal fun CreateAccountScreen(
    onUserLoggedIn: () -> Unit,
    onNext: () -> Unit,
    userState: CreateAccountViewModel.SignupUserState,
    setUserEmail: (String) -> Unit,
    setUserPassword: (String) -> Unit,
    setUserPasswordConfirmation: (String) -> Unit,
    requestCode: () -> Unit,
) {
    PreLoginArtistBackgroundContentTemplate {

        LaunchedEffect(
            key1 = userState.verificationRequested,
            key2 = userState.verificationRequested
        ) {
            if (userState.verificationRequested) {
                onNext()
            }
            if (userState.isUserRegistered) {
                onUserLoggedIn()
            }
        }

        val focusRequester = remember { FocusRequester() }

        val emailState = remember { EmailState() }
        Email(emailState = emailState, onImeAction = { focusRequester.requestFocus() })

        val passwordState1 = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState1,
            onImeAction = {},
            modifier = Modifier.focusRequester(focusRequester),
        )

        val passwordState2 = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState2,
            onImeAction = {},
            modifier = Modifier.focusRequester(focusRequester),
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondaryButton(text = "Nextttt") {
            if (passwordState1.isValid && passwordState2.isValid
                && passwordState1.text == passwordState2.text || true
            ) {
                setUserEmail("cescobar+2@newm.io")
                setUserPassword("Password18")
                setUserPasswordConfirmation("Password18")
                requestCode()
            }
        }
    }
}
