package io.newm.feature.login.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.utils.shortToast
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.Password
import io.newm.feature.login.screen.password.PasswordState

internal const val TAG_LOGIN_SCREEN = "TAG_LOGIN_SCREEN"

@Composable
fun LoginScreen(
    onUserLoggedIn: () -> Unit,
    viewModel: LoginViewModel = org.koin.androidx.compose.get()
) {
    val state by viewModel.state.collectAsState()

    LoginScreenContent(
        state = state,
        onUserLoggedIn = onUserLoggedIn,
        attemptToLogin = viewModel::attemptToLogin
    )
}

@Composable
internal fun LoginScreenContent(
    state: LoginViewModel.LoginState,
    onUserLoggedIn: () -> Unit,
    attemptToLogin: (email: String, password: String) -> Unit,
) {
    val context = LocalContext.current
    PreLoginArtistBackgroundContentTemplate {
        LaunchedEffect(state.isUserLoggedIn, state.errorMessage) {
            if (state.isUserLoggedIn) {
                context.shortToast("Enjoy!")
                onUserLoggedIn()
            }
            if (!state.errorMessage.isNullOrBlank()) {
                context.shortToast(state.errorMessage.orEmpty())
            }
        }
        val focusRequester = remember { FocusRequester() }
        val emailState = remember { EmailState() }
        Email(emailState = emailState, onImeAction = { focusRequester.requestFocus() })

        Spacer(modifier = Modifier.height(8.dp))

        val passwordState = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            onImeAction = {
                attemptToLogin(emailState.text, passwordState.text)
            },
            modifier = Modifier.focusRequester(focusRequester),
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Login",
            onClick = {
                attemptToLogin(
                    emailState.text, passwordState.text
                )
            }
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

@Preview
@Composable
private fun DefaultLightLoginScreenPreview() {
    NewmTheme(darkTheme = false) {
        LoginScreen(
            onUserLoggedIn = {},
        )
    }
}

@Preview
@Composable
private fun DefaultDarkLoginScreenPreview() {
    NewmTheme(darkTheme = true) {
        LoginScreen(
            onUserLoggedIn = {},
        )
    }
}
