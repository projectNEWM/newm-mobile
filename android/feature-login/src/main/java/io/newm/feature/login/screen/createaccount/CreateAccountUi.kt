package io.newm.feature.login.screen.createaccount

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.ui.Ui
import io.newm.feature.login.screen.createaccount.CreateAccountUiState.SignupForm
import io.newm.feature.login.screen.createaccount.signupform.SignUpFormUi

class CreateAccountUi : Ui<CreateAccountUiState> {
    @Composable
    override fun Content(state: CreateAccountUiState, modifier: Modifier) {
        when (state) {
            is SignupForm -> {
                SignUpFormUi(state = state)
            }
        }
    }
}
