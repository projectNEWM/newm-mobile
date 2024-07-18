package io.newm.feature.login.screen.welcome

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.theme.inter
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
            Text(
                text = stringResource(id = R.string.create_account),
                fontSize = 16.sp,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onEvent(CreateAccountClicked) }
            )

            LoginPageMainImage(R.drawable.ic_newm_logo)
            Text(
                text = stringResource(id = R.string.welcome_to_newm),
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(all = 16.dp)
            ) {
                PrimaryButton(
                    text = stringResource(id = R.string.login_with_email),
                    onClick = { onEvent(LoginClicked) },
                )
                SecondaryButton(
                    labelResId = R.string.login_with_google,
                    onClick = { onEvent(WelcomeScreenUiEvent.OnGoogleSignInClicked) },
                    iconResId = R.drawable.ic_google_g
                )
            }
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
