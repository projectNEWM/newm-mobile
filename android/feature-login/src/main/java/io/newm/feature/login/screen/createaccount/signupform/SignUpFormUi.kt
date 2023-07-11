package io.newm.feature.login.screen.createaccount.signupform

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.password.Password
import io.newm.core.resources.R
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.feature.login.screen.PreLoginArtistBackgroundContentTemplate
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.SignupForm

@Composable
fun SignUpFormUi(
    state: SignupForm,
) {
    val onEvent = state.eventSink
    val focusRequester = remember { FocusRequester() }

    PreLoginArtistBackgroundContentTemplate {
        Email(
            emailState = state.emailState,
            onImeAction = { focusRequester.requestFocus() }
        )

        Password(
            label = stringResource(id = R.string.password),
            passwordState = state.passwordState,
            onImeAction = {},
            modifier = Modifier.focusRequester(focusRequester),
        )

        Password(
            label = stringResource(id = R.string.password),
            passwordState = state.passwordConfirmationState,
            onImeAction = {},
            modifier = Modifier.focusRequester(focusRequester),
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondaryButton(text = "Next") {
            onEvent(SignupFormUiEvent.Next)
        }
    }
}
