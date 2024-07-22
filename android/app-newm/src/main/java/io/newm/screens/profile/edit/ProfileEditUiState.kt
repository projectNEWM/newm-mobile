package io.newm.screens.profile.edit

import com.slack.circuit.runtime.CircuitUiState
import io.newm.feature.login.screen.TextFieldState
import io.newm.screens.profile.ProfileEditUiEvent

sealed class ProfileEditUiState : CircuitUiState {
    data object Loading : ProfileEditUiState()
    data class Content(
        val profile: Profile,
        val firstName: TextFieldState,
        val lastName: TextFieldState,
        val canUserEditName: Boolean,
        val currentPasswordState: TextFieldState,
        val newPasswordState: TextFieldState,
        val confirmPasswordState: TextFieldState,
        val submitButtonEnabled: Boolean,
        val showConnectWallet: Boolean,
        val errorMessage: String?,
        val eventSink: (ProfileEditUiEvent) -> Unit
    ) : ProfileEditUiState() {
        data class Profile(
            val firstName: String,
            val lastName: String,
            val canUserEditName: Boolean,
            val email: String,
            val pictureUrl: String,
            val bannerUrl: String,
        )
    }
}
