package io.newm.sharedfeatures.screens.auth.resetpassword

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import io.newm.sharedfeatures.screens.auth.login.EmailState
import io.newm.sharedfeatures.screens.auth.login.PasswordState
import io.newm.sharedfeatures.screens.auth.login.TextFieldState
import io.newm.sharedfeatures.screens.auth.login.UiMessage

sealed interface ResetPasswordUiEvent : CircuitUiEvent {
    sealed interface EnterEmailUiEvent : ResetPasswordUiEvent {
        data object OnSubmit : EnterEmailUiEvent
    }

    sealed interface EnterVerificationCodeUiEvent : ResetPasswordUiEvent {
        data object OnSubmit : EnterVerificationCodeUiEvent
    }

    sealed interface EnterNewPasswordUiEvent : ResetPasswordUiEvent {
        data object OnSubmit : EnterNewPasswordUiEvent
    }
}

sealed interface ResetPasswordScreenUiState : CircuitUiState {
    val isLoading: Boolean
    val errorMessage: UiMessage?

    data class EnterEmail(
        val email: EmailState,
        override val isLoading: Boolean,
        override val errorMessage: UiMessage?,
        val submitButtonEnabled: Boolean,
        val eventSink: (ResetPasswordUiEvent.EnterEmailUiEvent) -> Unit,
    ) : ResetPasswordScreenUiState

    data class EnterVerificationCode(
        val code: TextFieldState,
        val submitButtonEnabled: Boolean,
        override val isLoading: Boolean,
        override val errorMessage: UiMessage?,
        val eventSink: (ResetPasswordUiEvent.EnterVerificationCodeUiEvent) -> Unit,
    ) : ResetPasswordScreenUiState

    data class EnterNewPassword(
        val password: PasswordState,
        val confirmPasswordState: PasswordState,
        val submitButtonEnabled: Boolean,
        override val isLoading: Boolean,
        override val errorMessage: UiMessage?,
        val eventSink: (ResetPasswordUiEvent.EnterNewPasswordUiEvent) -> Unit,
    ) : ResetPasswordScreenUiState
}
