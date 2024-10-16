package io.newm.feature.login.screen.createaccount

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.newm.core.ui.LoadingScreen
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.EmailVerificationUiState
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.EmailAndPasswordUiState

@Composable
fun CreateAccountUi(state: CreateAccountUiState, modifier: Modifier) {
    when (state) {
        is EmailAndPasswordUiState -> {
            EmailAndPasswordUi(modifier, state)
        }

        is EmailVerificationUiState -> {
            EmailVerificationUi(modifier, state)
        }

        CreateAccountUiState.Loading -> LoadingScreen()
    }
}

