package io.newm.sharedfeatures.devmenu

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import io.newm.shared.commonPublic.analytics.ScreenEvents
import io.newm.sharedfeatures.parceling.CommonParcelize

@CommonParcelize
object DevMenuMainScreen : Screen, ScreenEvents {
    sealed interface UiState : CircuitUiState {
        data object Loading : UiState
        data class Content(
            val menuItems: List<DevMenuItem>,
            val onEvent: (UiEvent) -> Unit,
        ) : UiState
    }

    sealed interface UiEvent : CircuitUiEvent {
        data object OnBack : UiEvent
        data class OnItemClick(val screen: Screen) : UiEvent
    }

    override val name: String
        get() = "DevMenuMainScreen"
}

data class DevMenuItem(
    val title: String,
    val screen: Screen,
    val description: String = ""
)
