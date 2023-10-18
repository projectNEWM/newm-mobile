package io.newm.feature.login.screen.createaccount

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import io.newm.feature.login.screen.TextFieldState

sealed interface CreateAccountUiState : CircuitUiState {
    object Loading : CreateAccountUiState

    data class EmailAndPasswordUiState(
        val passwordConfirmationState: TextFieldState,
        val passwordState: TextFieldState,
        val emailState: TextFieldState,
        val eventSink: (SignupFormUiEvent) -> Unit,
    ) : CreateAccountUiState

    data class EmailVerificationUiState(
        val verificationCode: TextFieldState,
        val errorMessage: String? = null,
        val eventSink: (EmailVerificationUiEvent) -> Unit,
    ) : CreateAccountUiState

    data class SetNameUiState(
        val name: TextFieldState,
        val eventSink: (SetNameUiEvent) -> Unit,
    ) : CreateAccountUiState
}

interface SetNameUiEvent : CircuitUiEvent {
    object Next : SetNameUiEvent
}

sealed interface EmailVerificationUiEvent : CircuitUiEvent {
    object Next : EmailVerificationUiEvent
}

sealed interface SignupFormUiEvent : CircuitUiEvent {
    object Next : SignupFormUiEvent
}