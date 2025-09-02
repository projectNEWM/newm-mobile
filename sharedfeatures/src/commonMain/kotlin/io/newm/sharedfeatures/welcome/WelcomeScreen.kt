package io.newm.sharedfeatures.welcome


import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.parceling.CommonParcelize

@CommonParcelize
data object WelcomeScreen : Screen {
    sealed interface UiState : CircuitUiState {
        data object Loading : UiState

        data class Content(
            val onEvent: (UiEvent) -> Unit,
        ) : UiState
    }

    sealed interface UiEvent : CircuitUiEvent {
        data object OnBack : UiEvent
        data object OnDevMenu : UiEvent
    }
}
