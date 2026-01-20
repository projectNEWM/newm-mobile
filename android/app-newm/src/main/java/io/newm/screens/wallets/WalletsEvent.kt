package io.newm.screens.wallets

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface WalletsEvent : CircuitUiEvent {
    data object OnBack : WalletsEvent

    data object OnRefresh : WalletsEvent

    data class OnDisconnectWallet(
        val walletId: String,
    ) : WalletsEvent

    data class OnConnectWallet(
        val newmCode: String,
    ) : WalletsEvent

    data class OnRenameWallet(
        val walletId: String,
        val newName: String,
    ) : WalletsEvent

    data class OnWalletDetailView(
        val walletId: String,
    ) : WalletsEvent
}
