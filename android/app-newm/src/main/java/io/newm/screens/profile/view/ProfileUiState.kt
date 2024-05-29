package io.newm.screens.profile.view

import com.slack.circuit.runtime.CircuitUiState
import io.newm.screens.profile.ProfileUiEvent
import io.newm.shared.public.models.User

sealed class ProfileUiState : CircuitUiState {
    data object Loading : ProfileUiState()
    data class Content(
        val profile: User,
        val isWalletConnected: Boolean,
        val eventSink: (ProfileUiEvent) -> Unit,
    ) : ProfileUiState()
}
