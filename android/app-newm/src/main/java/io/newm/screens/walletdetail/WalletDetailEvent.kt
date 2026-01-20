package io.newm.screens.walletdetail

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface WalletDetailEvent : CircuitUiEvent {
    data object OnBack : WalletDetailEvent

    data object OnRefresh : WalletDetailEvent
}
