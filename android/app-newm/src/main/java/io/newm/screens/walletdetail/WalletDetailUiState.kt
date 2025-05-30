package io.newm.screens.walletdetail

import com.slack.circuit.runtime.CircuitUiState
import io.newm.shared.public.models.WalletConnection

sealed interface WalletDetailUiState : CircuitUiState {
    val eventSink: (WalletDetailEvent) -> Unit
    val isSyncing: Boolean
    val walletName: String

    data class Loading(
        override val eventSink: (WalletDetailEvent) -> Unit,
        override val isSyncing: Boolean,
        override val walletName: String
    ) : WalletDetailUiState

    data class Error(
        override val eventSink: (WalletDetailEvent) -> Unit,
        override val isSyncing: Boolean,
        override val walletName: String
    ) : WalletDetailUiState

    data class Content(
        override val eventSink: (WalletDetailEvent) -> Unit,
        override val isSyncing: Boolean,
        override val walletName: String,
        val walletConnection: WalletConnection
    ) : WalletDetailUiState
}