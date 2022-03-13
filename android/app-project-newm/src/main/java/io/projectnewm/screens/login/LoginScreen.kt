package io.projectnewm.screens.login

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.projectnewm.R
import io.projectnewm.components.SongRingBrush
import io.projectnewm.screens.login.email.Email
import io.projectnewm.screens.login.email.EmailState
import io.projectnewm.screens.login.password.Password
import io.projectnewm.screens.login.password.PasswordState
import io.projectnewm.utilities.ToBeImplemented
import io.projectnewm.utilities.shortToast

internal const val TAG_LOGIN_SCREEN = "TAG_LOGIN_SCREEN"

@Composable
fun LoginScreen(
    onSignInSubmitted: (email: String, password: String) -> Unit,
    scrollState: ScrollState = rememberScrollState()
) {
    LoginPageBackgroundImage(backgroundImage = R.drawable.bg_login)

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .verticalScroll(scrollState)
            .testTag(TAG_LOGIN_SCREEN),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        LoginPageMainImage(R.drawable.ic_newm_logo)
        LoginPageMainTextImage(textImage = R.drawable.ic_login_enter_newmiverse)

        Spacer(modifier = Modifier.height(50.dp))

        val focusRequester = remember { FocusRequester() }
        val emailState = remember { EmailState() }
        Email(emailState = emailState, onImeAction = { focusRequester.requestFocus() })

        Spacer(modifier = Modifier.height(8.dp))

        val passwordState = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            onImeAction = {
                if (emailState.isValid && passwordState.isValid) {
                    onSignInSubmitted(emailState.text, passwordState.text)
                }
            },
            modifier = Modifier.focusRequester(focusRequester),
        )

        Spacer(modifier = Modifier.height(16.dp))

        ForgotPassword()

        Spacer(modifier = Modifier.height(32.dp))

        Box(Modifier.clickable {
            if (emailState.isValid && passwordState.isValid) {
                context.shortToast("Welcome ${emailState.text}")
                onSignInSubmitted(emailState.text, passwordState.text)
            } else if (emailState.isValid.not() && passwordState.isValid.not()) {
                context.shortToast("Please Enter a Valid Email and Password")
            } else if (emailState.isValid.not()) {
                context.shortToast("Please enter a valid Email")
            } else if (passwordState.isValid.not()) {
                context.shortToast("Please enter a valid Password")
            }
        }
        ) {
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
                    emailState.text = "guest@projectnewm.io"
                    onSignInSubmitted(emailState.text, passwordState.text)
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

        CreateAccount()
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
fun CreateAccount() {
    ToBeImplemented("Create Account Coming Soon!") {
        Text(
            text = "Or Create Your Free Account",
            style = TextStyle(textDecoration = TextDecoration.Underline),
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.onSurface
        )
    }
}


@Composable
private fun LoginPageMainImage(@DrawableRes mainImage: Int) {
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
private fun LoginPageBackgroundImage(@DrawableRes backgroundImage: Int) {
    Image(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        painter = painterResource(backgroundImage),
        contentDescription = "Newm Login Screen",
        contentScale = ContentScale.Crop,
    )
}
