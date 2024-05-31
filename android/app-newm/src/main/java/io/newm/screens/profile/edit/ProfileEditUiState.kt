package io.newm.screens.profile.edit

import com.slack.circuit.runtime.CircuitUiState
import io.newm.screens.profile.ProfileEditUiEvent
import io.newm.shared.public.models.User

sealed class ProfileEditUiState : CircuitUiState {
    data object Loading : ProfileEditUiState()
    data class Content(
        val profile: User,
        val submitButtonEnabled: Boolean,
        val showConnectWallet: Boolean,
        val eventSink: (ProfileEditUiEvent) -> Unit
    ) : ProfileEditUiState()
}
