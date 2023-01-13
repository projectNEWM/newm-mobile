package io.newm.feature.login.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.newm.core.ui.utils.SongRingBrush
import io.newm.core.ui.utils.ToBeImplemented
import io.newm.core.ui.utils.shortToast
import io.newm.core.resources.R
import io.newm.core.theme.NewmColors
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.utils.ActionButtonBackgroundBrush
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.Password
import io.newm.feature.login.screen.password.PasswordState
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.getViewModel

internal const val TAG_LOGIN_SCREEN = "TAG_LOGIN_SCREEN"

@Composable
fun LoginScreen(
    onUserLoggedIn: () -> Unit,
    viewModel: LoginViewModel = org.koin.androidx.compose.get()
) {
    val context = LocalContext.current
    PreLoginArtistBackgroundContentTemplate {
        val state = viewModel.state.collectAsState()
        LaunchedEffect(state.value.isUserLoggedIn, state.value.errorMessage) {
            if (state.value.isUserLoggedIn) {
                context.shortToast("Enjoy!")
                onUserLoggedIn()
            }
            if (!state.value.errorMessage.isNullOrBlank()) {
                context.shortToast(state.value.errorMessage.orEmpty())
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
                viewModel.attemptToLogin(email = emailState.text, passwordState.text)
            },
            modifier = Modifier.focusRequester(focusRequester),
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Login",
            onClick = {
                viewModel.attemptToLogin(
                    email = emailState.text, password = passwordState.text
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
