package io.newm.screens.forceupdate

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface ForceAppUpdateEvent : CircuitUiEvent {
    data object OnForceUpdate : ForceAppUpdateEvent
}