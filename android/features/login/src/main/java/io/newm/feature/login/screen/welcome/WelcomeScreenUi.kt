package io.newm.feature.login.screen.welcome

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
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.SignInButton
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.feature.login.screen.LoginPageMainImage
import io.newm.feature.login.screen.welcome.WelcomeScreenUiEvent.CreateAccountClicked
import io.newm.feature.login.screen.welcome.WelcomeScreenUiEvent.LoginClicked

@Composable
fun WelcomeScreenUi(
    modifier: Modifier = Modifier,
    state: WelcomeScreenUiState
) {
    val onEvent = state.onEvent

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginPageMainImage(R.drawable.ic_newm_logo)
            Text(
                text = "Welcome to NEWM",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SignInWithGoogleButton(onEvent)
                PrimaryButton(
                    text = "Login",
                    onClick = { onEvent(LoginClicked) },
                )
                SecondaryButton(
                    text = "Create new account",
                    onClick = { onEvent(CreateAccountClicked) }
                )
            }
        }
    }
}

@Composable
private fun SignInWithGoogleButton(onEvent: (WelcomeScreenUiEvent) -> Unit) {
    AndroidView(factory = { context -> SignInButton(context) }) { button ->
        button.apply {
            setSize(SignInButton.SIZE_WIDE)
            setOnClickListener { onEvent(WelcomeScreenUiEvent.OnGoogleSignInClicked) }
        }
    }
}

@Preview
@Composable
private fun DefaultLightWelcomePreview() {
    NewmTheme(darkTheme = false) {
        WelcomeScreenUi(
            state = WelcomeScreenUiState(onEvent = {})
        )
    }
}

@Preview
@Composable
private fun DefaultDarkWelcomePreview() {
    NewmTheme(darkTheme = true) {
        WelcomeScreenUi(
            state = WelcomeScreenUiState(onEvent = {})
        )
    }
}
