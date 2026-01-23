package io.newm.sharedfeatures.screens.auth.signup

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import io.newm.sharedfeatures.screens.auth.login.EmailState
import io.newm.sharedfeatures.screens.auth.login.PasswordState
import io.newm.sharedfeatures.screens.auth.login.TextFieldState
import io.newm.sharedfeatures.screens.auth.login.UiMessage

sealed interface CreateAccountUiState : CircuitUiState {
    data object Loading : CreateAccountUiState

    data class EmailAndPasswordUiState(
        val passwordConfirmationState: PasswordState,
        val passwordState: PasswordState,
        val emailState: EmailState,
        val submitButtonEnabled: Boolean,
        val errorMessage: UiMessage?,
        val eventSink: (SignupFormUiEvent) -> Unit,
    ) : CreateAccountUiState

    data class EmailVerificationUiState(
        val verificationCode: TextFieldState,
        val errorMessage: UiMessage?,
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
