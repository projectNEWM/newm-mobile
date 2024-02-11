package io.newm.feature.login.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.ui.Ui
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.ToastSideEffect
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.text.TextFieldWithLabelDefaults
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.login.LoginScreenUiState
import io.newm.feature.login.screen.login.LoginUiEvent
import io.newm.feature.login.screen.login.LoginUiEvent.ForgotPasswordClick
import io.newm.feature.login.screen.login.LoginUiEvent.OnLoginClick
import io.newm.feature.login.screen.password.Password

internal const val TAG_LOGIN_SCREEN = "TAG_LOGIN_SCREEN"

class LoginScreenUi : Ui<LoginScreenUiState> {
    @Composable
    override fun Content(state: LoginScreenUiState, modifier: Modifier) {
        LoginScreenContent(state = state)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun LoginScreenContent(
    state: LoginScreenUiState,
) {
    val eventSink = state.eventSink
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    ToastSideEffect(state.errorMessage)

    PreLoginArtistBackgroundContentTemplate(
        isLoading = state.isLoading,
        header = {
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { eventSink(ForgotPasswordClick) }) {
                Text("Forgot your password?")
            }
        }
    ) {
        Email(
            modifier = Modifier.focusRequester(focusRequester),
            emailState = state.emailState,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.EMAIL.copy(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Password(
            label = R.string.password,
            passwordState = state.passwordState,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    keyboardController?.hide()
                    if (state.submitButtonEnabled) {
                        eventSink(OnLoginClick)
                    }
                }
            ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Login",
            onClick = { eventSink(OnLoginClick) },
            enabled = state.submitButtonEnabled,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LoginPageMainImage(@DrawableRes mainImage: Int) {
    Image(
        modifier = Modifier
            .width(250.dp)
            .height(250.dp),
        painter = painterResource(mainImage),
        contentDescription = "Newm Login Logo",
        contentScale = ContentScale.Crop,
    )
}

@Composable
@Preview(showBackground = true)
private fun DefaultLightLoginScreenPreview() {
    NewmTheme(darkTheme = false) {
        LoginScreenContent(
            state = LoginScreenUiState(
                emailState = TextFieldState(),
                passwordState = TextFieldState(),
                submitButtonEnabled = true,
                errorMessage = null,
                isLoading = true,
                eventSink = {}
            ),
        )
    }
}

@Preview
@Composable
private fun DefaultDarkLoginScreenPreview() {
    NewmTheme(darkTheme = true) {
        LoginScreenContent(
            state = LoginScreenUiState(
                emailState = TextFieldState(),
                passwordState = TextFieldState(),
                submitButtonEnabled = true,
                errorMessage = null,
                isLoading = true,
                eventSink = {}
            ),
        )
    }
}
