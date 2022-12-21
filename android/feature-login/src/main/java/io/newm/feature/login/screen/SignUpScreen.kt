package io.newm.feature.login.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.newm.core.ui.utils.HotPinkBrush
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.email.EmailState
import io.newm.feature.login.screen.password.Password
import io.newm.feature.login.screen.password.PasswordState
import io.newm.core.resources.R

@Composable
fun SignUpScreen(
    onUserLoggedIn: () -> Unit,
    onVerification: () -> Unit,
    viewModel: SignupViewModel
) {
    PreLoginArtistBackgroundContentTemplate {

        val userState = viewModel.state.collectAsState()

        LaunchedEffect(
            key1 = userState.value.verificationRequested,
            key2 = userState.value.verificationRequested
        ) {
            if (userState.value.verificationRequested) {
                onVerification()
            }
            if (userState.value.isUserRegistered) {
                onUserLoggedIn()
            }
        }

        Text(text = "Welcome")
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

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(BorderStroke(1.dp, HotPinkBrush()))
                .background(brush = HotPinkBrush())
                .clickable {
                    if (emailState.isValid
                        && passwordState1.isValid && passwordState2.isValid
                        && passwordState1.text == passwordState2.text
                    ) {
                        viewModel.setUserEmail(emailState.text)
                        viewModel.setUserPassword(passwordState1.text)
                        viewModel.setUserPasswordConfirmation(passwordState2.text)
                        viewModel.requestCode()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Enter",
                color = MaterialTheme.colors.onSurface,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
