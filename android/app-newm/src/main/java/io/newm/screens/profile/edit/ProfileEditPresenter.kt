package io.newm.screens.profile.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.internal.rememberStableCoroutineScope
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.Logout
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.password.isPasswordValid
import io.newm.feature.login.screen.password.passwordValidationError
import io.newm.screens.Screen.PrivacyPolicy
import io.newm.screens.Screen.TermsAndConditions
import io.newm.screens.profile.OnBack
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnSaveProfile
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.shared.public.models.User
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.HasWalletConnectionsUseCase
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlinx.coroutines.launch

class ProfileEditPresenter(
    private val navigator: Navigator,
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase,
    private val userDetailsUseCase: UserDetailsUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val logout: Logout,
) : Presenter<ProfileEditUiState> {
    @Composable
    override fun present(): ProfileEditUiState {
        val storedUser by remember {
            userDetailsUseCase.fetchLoggedInUserDetailsFlow()
        }.collectAsState(initial = null)

        val isWalletConnected by remember {
            hasWalletConnectionsUseCase.hasWalletConnectionsFlow()
        }.collectAsState(initial = false)

        val profile = remember(storedUser) {
            storedUser?.let { user ->
                ProfileEditUiState.Content.Profile(
                    pictureUrl = user.pictureUrl.orEmpty(),
                    bannerUrl = user.bannerUrl.orEmpty(),
                    nickname = user.nickname.orEmpty(),
                    email = user.email.orEmpty(),
                )
            }
        }

        val nicknameState = remember(profile?.nickname) {
            TextFieldState(profile?.nickname.orEmpty())
        }

        val currentPasswordState = remember {
            TextFieldState()
        }

        val newPasswordState = remember {
            TextFieldState()
        }

        val confirmPasswordState = remember {
            TextFieldState()
        }
        
        var errorMessage by remember {
            mutableStateOf<String?>(null)
        }

        val isFormDirty =
            remember(
                nicknameState.isFocusedDirty,
                currentPasswordState.isFocusedDirty,
                newPasswordState.isFocusedDirty,
                confirmPasswordState.isFocusedDirty
            ) {
                listOf(
                    nicknameState,
                    currentPasswordState,
                    newPasswordState,
                    confirmPasswordState
                ).any { it.isFocusedDirty }
            }

        val coroutineScope = rememberStableCoroutineScope()

        return if (profile == null) {
            ProfileEditUiState.Loading
        } else {
            ProfileEditUiState.Content(
                profile = profile,
                errorMessage = errorMessage,
                submitButtonEnabled = isFormDirty,
                nicknameState = nicknameState,
                currentPasswordState = currentPasswordState,
                newPasswordState = newPasswordState,
                confirmPasswordState = confirmPasswordState,
                showConnectWallet = !isWalletConnected,
            ) { event ->
                when (event) {
                    is OnSaveProfile -> coroutineScope.launch {
                        try {
                            val error = getFormErrorOrNull(
                                currentPasswordState,
                                newPasswordState,
                                confirmPasswordState,
                                nicknameState
                            )

                            errorMessage = error

                            if (error != null) {
                                return@launch
                            }

                            val profile = User(
                                newPassword = newPasswordState.text.takeIf { it.isNotEmpty() },
                                currentPassword = currentPasswordState.text.takeIf { it.isNotEmpty() },
                                confirmPassword = confirmPasswordState.text.takeIf { it.isNotEmpty() },
                                nickname = nicknameState.text,
                                createdAt = "",
                                id = ""
                            )
                            userDetailsUseCase.updateUserDetails(profile)
                            navigator.pop()
                        } catch (e: Throwable) {
                            e.printStackTrace()
                            errorMessage = "An error occurred. Please try again."
                        }
                    }

                    is OnConnectWallet -> coroutineScope.launch {
                        connectWalletUseCase.connect(event.xpub)
                    }

                    OnLogout -> logout.signOutUser()
                    OnShowTermsAndConditions -> navigator.goTo(TermsAndConditions)
                    OnShowPrivacyPolicy -> navigator.goTo(PrivacyPolicy)
                    OnBack -> navigator.pop()
                }
            }
        }
    }

    /**
     * Returns an error message if the form is invalid, or null if the form is valid.
     *
     * The form is invalid if any of the following conditions are met:
     *
     * - The new password is not empty and does not match the correct format.
     * - The confirm password does not match the new password.
     * - The new password is not empty, but the confirm password is empty.
     * - The new password is not empty, but the current password is empty.
     */

    private fun getFormErrorOrNull(
        currentPasswordState: TextFieldState,
        newPasswordState: TextFieldState,
        confirmPasswordState: TextFieldState,
        nicknameState: TextFieldState
    ): String? {
        if (newPasswordState.text.isNotEmpty() && isPasswordValid(newPasswordState.text).not()) {
            return passwordValidationError(newPasswordState.text)
        }

        if (newPasswordState.text.isNotEmpty() && newPasswordState.text != confirmPasswordState.text) {
            return "Passwords do not match"
        }

        if (newPasswordState.text.isNotEmpty() && confirmPasswordState.text.isEmpty()) {
            return "Please confirm your new password"
        }

        if (newPasswordState.text.isNotEmpty() && currentPasswordState.text.isEmpty()) {
            return "Please enter your current password"
        }

        if (nicknameState.text.isEmpty()) {
            return "Please enter a nickname"
        }

        return null
    }
}
