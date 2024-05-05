package io.newm.screens.account

import com.slack.circuit.runtime.CircuitUiState
import io.newm.shared.public.models.User

sealed class UserAccountState : CircuitUiState {
    data object Loading : UserAccountState()
    data class Content(
        val profile: User,
        val isWalletConnected: Boolean,
        val showWalletConnectButton: Boolean,
        val eventSink: (UserAccountEvent) -> Unit,
    ) : UserAccountState()
}
