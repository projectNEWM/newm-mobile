package io.newm.feature.login.screen.createaccount.signupform

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface SignupFormUiEvent : CircuitUiEvent {
    object Next : SignupFormUiEvent
}
