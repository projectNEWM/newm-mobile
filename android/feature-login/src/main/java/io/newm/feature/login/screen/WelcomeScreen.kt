package io.newm.feature.login.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton

@Composable
fun WelcomeScreen(onLogin: () -> Unit, onCreateAccount: () -> Unit, onContinueAsGuest: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginPageMainImage(io.newm.core.resources.R.drawable.ic_newm_logo)
            Text(
                text = "Welcome to NEWM",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(text = "Login", onClick = onLogin)
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(text = "Create new account", onClick = onCreateAccount)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun DefaultLightWelcomePreview() {
    NewmTheme(darkTheme = false) {
        WelcomeScreen(
            onLogin = {},
            onCreateAccount = {},
            onContinueAsGuest = {}
        )
    }
}

@Preview
@Composable
private fun DefaultDarkWelcomePreview() {
    NewmTheme(darkTheme = true) {
        WelcomeScreen(
            onLogin = {},
            onCreateAccount = {},
            onContinueAsGuest = {}
        )
    }
}