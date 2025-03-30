package io.newm.feature.login.screen.createaccount

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import io.newm.feature.login.screen.TextFieldState

sealed interface CreateAccountUiState : CircuitUiState {
    data object Loading : CreateAccountUiState

    data class EmailAndPasswordUiState(
        val passwordConfirmationState: TextFieldState,
        val passwordState: TextFieldState,
        val emailState: TextFieldState,
        val submitButtonEnabled: Boolean,
        val errorMessage: String?,
        val eventSink: (SignupFormUiEvent) -> Unit,
    ) : CreateAccountUiState

    data class EmailVerificationUiState(
        val verificationCode: TextFieldState,
        val errorMessage: String?,
        val nextButtonEnabled: Boolean,
        val eventSink: (EmailVerificationUiEvent) -> Unit,
    ) : CreateAccountUiState
}

sealed interface EmailVerificationUiEvent : CircuitUiEvent {
    data object Next : EmailVerificationUiEvent
}

sealed interface SignupFormUiEvent : CircuitUiEvent {
    data object Next : SignupFormUiEvent
}
