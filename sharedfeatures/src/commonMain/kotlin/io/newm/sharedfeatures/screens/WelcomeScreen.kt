package io.newm.sharedfeatures.screens

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.parceling.CommonParcelize

@CommonParcelize
data object WelcomeScreen : Screen {
    sealed interface UiState : CircuitUiState {
        data class Content(
            val onEvent: (UiEvent) -> Unit,
        ) : UiState
    }

    sealed interface UiEvent : CircuitUiEvent {
        data object OnBack : UiEvent

        data object OnLogin : UiEvent

        data object CreateAccountClicked : UiEvent

        data object OnGoogleSignInClicked : UiEvent

        data object OnTermsOfServiceClicked : UiEvent

        data object OnPrivacyPolicyClicked : UiEvent
    }
}
