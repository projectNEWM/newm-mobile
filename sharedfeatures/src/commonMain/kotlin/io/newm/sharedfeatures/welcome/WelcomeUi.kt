package io.newm.sharedfeatures.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import io.newm.core.ui.OnboardingMainImage
import io.newm.core.ui.PrivacyPolicyAndTermsSection
import io.newm.core.ui.ToastSideEffect
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.sharedfeatures.screens.WelcomeScreen
import io.newm.sharedfeatures.screens.WelcomeScreen.UiState
import me.tatarka.inject.annotations.Inject
import newm_mobile.sharedfeatures.generated.resources.Res
import newm_mobile.sharedfeatures.generated.resources.create_account
import newm_mobile.sharedfeatures.generated.resources.ic_google_g
import newm_mobile.sharedfeatures.generated.resources.ic_newm_logo
import newm_mobile.sharedfeatures.generated.resources.login_with_email
import newm_mobile.sharedfeatures.generated.resources.login_with_google
import newm_mobile.sharedfeatures.generated.resources.welcome_to_newm
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import newm_mobile.sharedfeatures.generated.resources.Res as R


@Composable
fun WelcomeUi(state: UiState, modifier: Modifier) {
    when (state) {
        is UiState.Content -> {
            state.errorMessage?.let { msg ->
                ToastSideEffect(msg)
            }
            WelcomeScreenContent(
                modifier = modifier,
                onCreateAccount = { },
                onLoginWithEmail = {
                   state.onEvent(WelcomeScreen.UiEvent.OnLogin)
                },
                onGoogleSignIn = {
                    state.onEvent(WelcomeScreen.UiEvent.OnGoogleSignIn)
                },
                onPrivacyPolicyClicked = {},
                onTermsOfServiceClicked = {},
                onDebugMenu = {
                    state.onEvent(WelcomeScreen.UiEvent.OnDevMenu)
                }
            )
        }

        UiState.Loading -> {
            Text("Welcome to Newm!")
        }
    }
}

@Composable
fun WelcomeScreenContent(
    modifier: Modifier,
    onCreateAccount: () -> Unit,
    onLoginWithEmail: () -> Unit,
    onGoogleSignIn: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit,
    onTermsOfServiceClicked: () -> Unit,
    onDebugMenu: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.create_account),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
                    .clickable(onClick = onCreateAccount)
            )

            OnboardingMainImage(painterResource(Res.drawable.ic_newm_logo))
            Text(
                text = stringResource(R.string.welcome_to_newm),
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
                    text = stringResource(R.string.login_with_email),
                    onClick = onLoginWithEmail,
                )
                SecondaryButton(
                    label = stringResource(Res.string.login_with_google),
                    onClick = onGoogleSignIn,
                    iconPainter = painterResource(Res.drawable.ic_google_g)
                )
            }

            PrivacyPolicyAndTermsSection(
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 16.dp),
                onPrivacyPolicyClicked = onPrivacyPolicyClicked,
                onTermsOfServiceClicked = onTermsOfServiceClicked,
            )
        }
    }
}


class WelcomeUiFactory @Inject constructor() : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            WelcomeScreen -> ui<UiState> { state, modifier ->
                WelcomeUi(state, modifier)
            }

            else -> null
        }
    }
}