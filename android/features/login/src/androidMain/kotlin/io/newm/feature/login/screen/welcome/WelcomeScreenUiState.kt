package io.newm.feature.login.screen.welcome

import com.slack.circuit.runtime.CircuitUiState

data class WelcomeScreenUiState(
    val onEvent: (WelcomeScreenUiEvent) -> Unit
) : CircuitUiState
