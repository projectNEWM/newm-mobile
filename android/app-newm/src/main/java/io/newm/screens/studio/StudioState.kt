package io.newm.screens.studio

import com.slack.circuit.runtime.CircuitUiState

sealed class StudioState : CircuitUiState {
    data object Loading : StudioState()

    data class Content(
        val eventSink: (StudioUiEvent) -> Unit,
        val accessToken: String? = null,
        val refreshToken: String? = null,
    ) : StudioState()

    data object Error : StudioState()
}
