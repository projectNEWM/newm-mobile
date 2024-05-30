package io.newm.screens.profile.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.internal.rememberStableCoroutineScope
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.Logout
import io.newm.screens.Screen.PrivacyPolicy
import io.newm.screens.Screen.TermsAndConditions
import io.newm.screens.profile.OnBack
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnSaveProfile
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlinx.coroutines.launch

class ProfileEditPresenter(
    private val navigator: Navigator,
    private val userDetailsUseCase: UserDetailsUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val logout: Logout,
) : Presenter<ProfileEditUiState> {
    @Composable
    override fun present(): ProfileEditUiState {
        val user by remember {
            userDetailsUseCase.fetchLoggedInUserDetailsFlow()
        }.collectAsState(initial = null)

        val coroutineScope = rememberStableCoroutineScope()

        return if (user == null) {
            ProfileEditUiState.Loading
        } else {
            ProfileEditUiState.Content(profile = user!!) { event ->
                when (event) {
                    is OnSaveProfile -> TODO()
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
}
