package io.newm.feature.login.screen.createaccount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.createaccount.signupform.SignupFormUiEvent
import io.newm.feature.login.screen.password.ConfirmPasswordState
import io.newm.feature.login.screen.password.PasswordState

class CreateAccountPresenter : Presenter<CreateAccountUiState> {
    @Composable
    override fun present(): CreateAccountUiState {
        val userEmail = rememberRetained { TextFieldState() }
        val password = rememberRetained { PasswordState() }
        val passwordConfirmation = rememberRetained { ConfirmPasswordState(password) }

        return CreateAccountUiState.SignupForm(
            emailState = userEmail,
            passwordState = password,
            passwordConfirmationState = passwordConfirmation,
        ) { event ->
            when (event) {
                SignupFormUiEvent.Next -> TODO()
            }
        }
    }
}
