package io.newm.screens.profile.view

import com.slack.circuit.runtime.CircuitUiState
import io.newm.screens.profile.ProfileUiEvent
import io.newm.shared.public.models.User
import io.newm.shared.public.models.WalletConnection

sealed class ProfileUiState : CircuitUiState {
    data object Loading : ProfileUiState()
    data class Content(
        val profile: User,
        val isWalletConnected: Boolean,
        val userConnectedWallets: List<WalletConnection> = emptyList(),
        val eventSink: (ProfileUiEvent) -> Unit,
        val showRecordStore: Boolean,
        val showMultiWallets: Boolean,
        val showStudio: Boolean
    ) : ProfileUiState()
}
