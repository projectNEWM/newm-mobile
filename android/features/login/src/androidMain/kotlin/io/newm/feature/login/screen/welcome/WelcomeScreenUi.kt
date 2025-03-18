package io.newm.feature.login.screen.welcome

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.newm.core.theme.NewmTheme
import io.newm.feature.login.screen.welcome.WelcomeScreenUiEvent.CreateAccountClicked
import io.newm.feature.login.screen.welcome.WelcomeScreenUiEvent.LoginClicked
import io.newm.sharedfeatures.welcome.WelcomeScreenContent

@Composable
fun WelcomeScreenUi(
    modifier: Modifier = Modifier,
    state: WelcomeScreenUiState,
) {
    val onEvent = state.onEvent

    WelcomeScreenContent(
        modifier = modifier,
        onCreateAccount = { onEvent(CreateAccountClicked) },
        onLoginWithEmail = { onEvent(LoginClicked) },
        onGoogleSignIn = { onEvent(WelcomeScreenUiEvent.OnGoogleSignInClicked) },
        onPrivacyPolicyClicked = { onEvent(WelcomeScreenUiEvent.OnPrivacyPolicyClicked) },
        onTermsOfServiceClicked = { onEvent(WelcomeScreenUiEvent.OnTermsOfServiceClicked) },
    )
}


@Preview
@Composable
private fun DefaultLightWelcomePreview() {
    NewmTheme(darkTheme = false) {
        WelcomeScreenUi(
            state = WelcomeScreenUiState(onEvent = {}),
        )
    }
}

@Preview
@Composable
private fun DefaultDarkWelcomePreview() {
    NewmTheme(darkTheme = true) {
        WelcomeScreenUi(
            state = WelcomeScreenUiState(onEvent = {}),
        )
    }
}
