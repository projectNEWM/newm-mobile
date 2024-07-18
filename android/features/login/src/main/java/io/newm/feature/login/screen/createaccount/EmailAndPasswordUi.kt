package io.newm.feature.login.screen.createaccount

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.password.Password
import io.newm.core.resources.R
import io.newm.core.ui.ToastSideEffect
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.text.TextFieldWithLabelDefaults
import io.newm.feature.login.screen.PreLoginArtistBackgroundContentTemplate
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.EmailAndPasswordUiState
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.ConfirmPasswordState
import io.newm.feature.login.screen.password.PasswordState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailAndPasswordUi(
    modifier: Modifier,
    state: EmailAndPasswordUiState,
) {
    val onEvent = state.eventSink
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    ToastSideEffect(message = state.errorMessage)

    PreLoginArtistBackgroundContentTemplate(
        modifier = modifier,
    ) {
        Email(
            modifier = Modifier.focusRequester(focusRequester),
            emailState = state.emailState,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.EMAIL.copy(imeAction = ImeAction.Next),
        )

        Password(
            label = R.string.password,
            passwordState = state.passwordState,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(imeAction = ImeAction.Next),
        )

        Password(
            label = R.string.confirm_password,
            passwordState = state.passwordConfirmationState,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    keyboardController?.hide()
                    if (state.submitButtonEnabled) {
                        onEvent(SignupFormUiEvent.Next)
                    }
                }
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.next),
            onClick = {
                onEvent(SignupFormUiEvent.Next)
            },
            enabled = state.submitButtonEnabled
        )
    }
}

@Preview
@Composable
fun EmailAndPasswordUiPreview() {
    EmailAndPasswordUi(
        modifier = Modifier.fillMaxSize(),
        state = EmailAndPasswordUiState(
            emailState = EmailState(),
            passwordState = PasswordState(),
            passwordConfirmationState = ConfirmPasswordState(PasswordState()),
            submitButtonEnabled = true,
            errorMessage = null,
        ) { }
    )
}

