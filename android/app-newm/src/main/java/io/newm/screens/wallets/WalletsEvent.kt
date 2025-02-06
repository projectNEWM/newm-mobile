package io.newm.screens.wallets

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface WalletsEvent : CircuitUiEvent {
    data object OnBack : WalletsEvent
    data object OnRefresh : WalletsEvent
    data object OnDisconnectAllWallets : WalletsEvent
    data class OnDisconnectWallet(val walletId: String) : WalletsEvent
    data class OnConnectWallet(val newmCode: String) : WalletsEvent
}