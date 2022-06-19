package io.projectnewm.feature.login.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.projectnewm.core.ui.utils.SongRingBrush
import io.projectnewm.core.ui.utils.ToBeImplemented
import io.projectnewm.core.ui.utils.shortToast
import io.projectnewm.core.resources.R
import io.projectnewm.feature.login.screen.email.Email
import io.projectnewm.feature.login.screen.email.EmailState
import io.projectnewm.feature.login.screen.password.Password
import io.projectnewm.feature.login.screen.password.PasswordState

internal const val TAG_LOGIN_SCREEN = "TAG_LOGIN_SCREEN"

@Composable
fun LoginScreen(
    onUserLoggedIn: () -> Unit,
    onSignupClick: () -> Unit,
    viewModel: LoginViewModel = org.koin.androidx.compose.get()
) {
    val context = LocalContext.current
    PreLoginArtistBackgroundContentTemplate {
        LoginPageMainTextImage(textImage = R.drawable.ic_login_enter_newmiverse)

        val state = viewModel.state.collectAsState()
        LaunchedEffect(state.value.isUserLoggedIn, state.value.errorMessage) {
            if (state.value.isUserLoggedIn) {
                context.shortToast("Enjoy!")
                onUserLoggedIn()
            }
            if(!state.value.errorMessage.isNullOrBlank()) {
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

        Spacer(modifier = Modifier.height(16.dp))

        ForgotPassword()

        Spacer(modifier = Modifier.height(32.dp))

        Box(Modifier.clickable {
            viewModel.attemptToLogin(
                email = emailState.text, password = passwordState.text
            )
        }) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                painter = painterResource(R.drawable.ic_login_enter_newm_button),
                contentDescription = "Newm Login Welcome Text",
                contentScale = ContentScale.FillHeight,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(BorderStroke(1.dp, SongRingBrush()))
                .clickable {
                    onUserLoggedIn()
                    context.shortToast("Welcome Guest")
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Continue as Guest",
                color = MaterialTheme.colors.onSurface,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CreateAccount(onSignupClick)
    }
}

@Composable
fun ForgotPassword() {
    ToBeImplemented("Forgot Password Coming Soon!") {
        Text(
            text = "Uh Oh! I Forgot My Password",
            style = TextStyle(textDecoration = TextDecoration.Underline),
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun CreateAccount(onSignupClick: () -> Unit) {
    Text(
        text = "Or Create Your Free Account",
        style = TextStyle(textDecoration = TextDecoration.Underline),
        textAlign = TextAlign.End,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier.clickable { onSignupClick() }
    )
}


@Composable
fun LoginPageMainImage(@DrawableRes mainImage: Int) {
    Image(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp),
        painter = painterResource(mainImage),
        contentDescription = "Newm Login Logo",
        contentScale = ContentScale.Crop,
    )

}

@Composable
private fun LoginPageMainTextImage(@DrawableRes textImage: Int) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        painter = painterResource(textImage),
        contentDescription = "Newm Login Welcome Text",
        contentScale = ContentScale.FillHeight,
    )
}

@Composable
internal fun LoginPageBackgroundImage(@DrawableRes backgroundImage: Int) {
    Image(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        painter = painterResource(backgroundImage),
        contentDescription = "Newm Login Screen",
        contentScale = ContentScale.Crop,
    )
}
