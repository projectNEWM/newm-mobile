package io.newm.feature.login.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.core.ui.text.TextFieldWithLabelDefaults
import io.newm.core.ui.utils.shortToast
import org.koin.compose.koinInject

internal const val TAG_LOGIN_SCREEN = "TAG_LOGIN_SCREEN"

@Composable
fun LoginScreen(
    onUserLoggedIn: () -> Unit,
    viewModel: LoginViewModel = koinInject()
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
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        TextFieldWithLabel(
            labelResId = R.string.email,
            onValueChange = { value ->
                email = value
            },
            enabled = true,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.EMAIL.copy(
                imeAction = ImeAction.Next
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldWithLabel(
            labelResId = R.string.password,
            onValueChange = { value ->
                password = value
            },
            isPassword = true,
            keyboardOptions = TextFieldWithLabelDefaults.KeyboardOptions.PASSWORD.copy(
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    attemptToLogin(
                        email, password
                    )
                }
            ),
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Login",
            onClick = {
                attemptToLogin(
                    email, password
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
