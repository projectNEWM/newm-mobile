package io.newm.screens.forceupdate

import com.slack.circuit.runtime.CircuitUiState

sealed class ForceAppUpdateState : CircuitUiState {
    data class Content(
        val eventSink: (ForceAppUpdateEvent) -> Unit
    ) : ForceAppUpdateState()
}