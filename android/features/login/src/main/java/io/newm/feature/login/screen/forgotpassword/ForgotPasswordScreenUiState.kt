package io.newm.feature.login.screen.forgotpassword

import com.slack.circuit.runtime.CircuitUiState
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordUiEvent.EnterEmailUiEvent
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordUiEvent.EnterVerificationCodeUiEvent
import io.newm.feature.login.screen.forgotpassword.ForgotPasswordUiEvent.SetNewPasswordUiEvent

sealed interface ForgotPasswordScreenUiState : CircuitUiState {
    val isLoading: Boolean
    val errorMessage: String?

    data class EnterEmail(
        val email: TextFieldState,
        override val isLoading: Boolean,
        override val errorMessage: String?,
        val submitButtonEnabled: Boolean,
        val eventSink: (EnterEmailUiEvent) -> Unit
    ) : ForgotPasswordScreenUiState

    data class EnterVerificationCode(
        val code: TextFieldState,
        val submitButtonEnabled: Boolean,
        override val isLoading: Boolean,
        override val errorMessage: String?,
        val eventSink: (EnterVerificationCodeUiEvent) -> Unit
    ) : ForgotPasswordScreenUiState

    data class SetNewPassword(
        val password: TextFieldState,
        val confirmPasswordState: TextFieldState,
        override val isLoading: Boolean,
        override val errorMessage: String?,
        val eventSink: (SetNewPasswordUiEvent) -> Unit
    ) : ForgotPasswordScreenUiState
}
