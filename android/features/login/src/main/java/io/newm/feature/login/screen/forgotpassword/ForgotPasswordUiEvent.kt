package io.newm.feature.login.screen.forgotpassword

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface ForgotPasswordUiEvent : CircuitUiEvent{
    sealed interface EnterEmailUiEvent: ForgotPasswordUiEvent {
        data object OnSubmit : EnterEmailUiEvent
    }

    sealed interface EnterVerificationCodeUiEvent: ForgotPasswordUiEvent
    sealed interface SetNewPasswordUiEvent: ForgotPasswordUiEvent
}
