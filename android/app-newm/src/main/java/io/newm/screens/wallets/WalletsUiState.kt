package io.newm.screens.wallets

import com.slack.circuit.runtime.CircuitUiState
import io.newm.shared.commonPublic.models.WalletConnection

sealed interface WalletsUiState : CircuitUiState {
    val eventSink: (WalletsEvent) -> Unit
    val isRefreshing: Boolean

    data class Empty(
        override val eventSink: (WalletsEvent) -> Unit,
        override val isRefreshing: Boolean
    ) : WalletsUiState

    data class Loading(
        override val eventSink: (WalletsEvent) -> Unit,
        override val isRefreshing: Boolean
    ) : WalletsUiState

    data class Content(
        override val eventSink: (WalletsEvent) -> Unit,
        val wallets: List<WalletConnection>,
        override val isRefreshing: Boolean
    ) : WalletsUiState
}