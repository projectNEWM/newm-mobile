package io.newm.screens.profile.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.internal.rememberStableCoroutineScope
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.Logout
import io.newm.screens.Screen.EditProfile
import io.newm.screens.Screen.PrivacyPolicy
import io.newm.screens.Screen.TermsAndConditions
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnDisconnectWallet
import io.newm.screens.profile.OnEditProfile
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlinx.coroutines.launch

class ProfilePresenter(
    private val navigator: Navigator,
    private val userDetailsUseCase: UserDetailsUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val logout: Logout,
) : Presenter<ProfileUiState> {
    @Composable
    override fun present(): ProfileUiState {
        val isWalletConnected by remember { connectWalletUseCase.hasWalletConnections() }.collectAsState(
            false
        )

        val user by remember { userDetailsUseCase.fetchLoggedInUserDetailsFlow() }.collectAsState(
            null
        )

        return if (user == null) {
            ProfileUiState.Loading
        } else {
            val coroutineScope = rememberStableCoroutineScope()

            ProfileUiState.Content(
                profile = user!!,
                isWalletConnected = isWalletConnected,
                eventSink = { event ->
                    when (event) {
                        is OnConnectWallet -> coroutineScope.launch {
                            connectWalletUseCase.connect(event.xpub)
                        }

                        OnDisconnectWallet -> coroutineScope.launch {
                            connectWalletUseCase.disconnect()
                        }

                        OnEditProfile -> navigator.goTo(EditProfile)
                        OnLogout -> logout.signOutUser()
                        OnShowTermsAndConditions -> navigator.goTo(TermsAndConditions)
                        OnShowPrivacyPolicy -> navigator.goTo(PrivacyPolicy)
                    }
                }
            )
        }
    }
}

