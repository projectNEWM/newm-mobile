package io.newm.feature.login.screen.resetpassword

import com.slack.circuit.runtime.CircuitUiState
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.resetpassword.ResetPasswordUiEvent.EnterEmailUiEvent
import io.newm.feature.login.screen.resetpassword.ResetPasswordUiEvent.EnterVerificationCodeUiEvent
import io.newm.feature.login.screen.resetpassword.ResetPasswordUiEvent.EnterNewPasswordUiEvent

sealed interface ResetPasswordScreenUiState : CircuitUiState {
    val isLoading: Boolean
    val errorMessage: String?

    data class EnterEmail(
        val email: TextFieldState,
        override val isLoading: Boolean,
        override val errorMessage: String?,
        val submitButtonEnabled: Boolean,
        val eventSink: (EnterEmailUiEvent) -> Unit
    ) : ResetPasswordScreenUiState

    data class EnterVerificationCode(
        val code: TextFieldState,
        val submitButtonEnabled: Boolean,
        override val isLoading: Boolean,
        override val errorMessage: String?,
        val eventSink: (EnterVerificationCodeUiEvent) -> Unit
    ) : ResetPasswordScreenUiState

    data class EnterNewPassword(
        val password: TextFieldState,
        val confirmPasswordState: TextFieldState,
        val submitButtonEnabled: Boolean,
        override val isLoading: Boolean,
        override val errorMessage: String?,
        val eventSink: (EnterNewPasswordUiEvent) -> Unit
    ) : ResetPasswordScreenUiState
}
