package io.newm.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.ui.Ui

inline fun <UiState : CircuitUiState> ui(
    crossinline body: @Composable (state: UiState, modifier: Modifier) -> Unit
): Ui<UiState> {
    return object : Ui<UiState> {
        @Composable
        override fun Content(state: UiState, modifier: Modifier) {
            body(state, modifier)
        }
    }
}
