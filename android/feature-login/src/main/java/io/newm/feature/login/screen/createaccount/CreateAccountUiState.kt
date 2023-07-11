package io.newm.feature.login.screen.createaccount

import com.slack.circuit.runtime.CircuitUiState
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.createaccount.signupform.SignupFormUiEvent

sealed interface CreateAccountUiState : CircuitUiState {
    data class SignupForm(
        val passwordConfirmationState: TextFieldState,
        val passwordState: TextFieldState,
        val emailState: TextFieldState,
        val eventSink: (SignupFormUiEvent) -> Unit,
    ) : CreateAccountUiState
}
