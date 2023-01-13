package io.newm.feature.login.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
    PreLoginArtistBackgroundContentTemplate {

        val userState = viewModel.state.collectAsState()

        LaunchedEffect(
            key1 = userState.value.verificationRequested,
            key2 = userState.value.verificationRequested
        ) {
            if (userState.value.verificationRequested) {
                onNext()
            }
            if (userState.value.isUserRegistered) {
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

        SecondaryButton(text = "Next") {
            if (passwordState1.isValid && passwordState2.isValid
                && passwordState1.text == passwordState2.text || true
            ) {
                viewModel.setUserEmail("cescobar+2@newm.io")
                viewModel.setUserPassword("Password18")
                viewModel.setUserPasswordConfirmation("Password18")
                viewModel.requestCode()
            }
        }
    }
}
