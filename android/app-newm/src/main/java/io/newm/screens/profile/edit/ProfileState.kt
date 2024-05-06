package io.newm.screens.profile.edit

import com.slack.circuit.runtime.CircuitUiState
import io.newm.shared.public.models.User

sealed class ProfileState : CircuitUiState {
    data object Loading : ProfileState()
    data class Content(
        val profile: User,
        val eventSink: (ProfileEvent) -> Unit
    ) : ProfileState()
}
