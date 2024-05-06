package io.newm.screens.account

import com.slack.circuit.runtime.CircuitUiEvent

sealed interface UserAccountEvent : CircuitUiEvent {
    data class OnConnectWallet(val xpubKey: String) : UserAccountEvent
    data object OnLogout : UserAccountEvent
    data object OnEditProfile : UserAccountEvent
    data object OnDisconnectWallet : UserAccountEvent
}
