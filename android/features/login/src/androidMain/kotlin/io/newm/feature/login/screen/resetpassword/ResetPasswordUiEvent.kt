package io.newm.feature.login.screen.resetpassword

import com.slack.circuit.runtime.CircuitUiEvent

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
