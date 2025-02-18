package newm.ui.screens.welcome


import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import newm.parceling.CommonParcelize

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
    }
}
