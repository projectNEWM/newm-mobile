package io.newm.screens.profile.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.screens.profile.OnBack
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnSaveProfile
import io.newm.screens.profile.OnShowAskTheCommunity
import io.newm.screens.profile.OnShowDocuments
import io.newm.screens.profile.OnShowFaq
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.shared.public.usecases.UserDetailsUseCase

class ProfileEditPresenter(
    private val navigator: Navigator,
    private val userDetailsUseCase: UserDetailsUseCase
) : Presenter<ProfileEditUiState> {
    @Composable
    override fun present(): ProfileEditUiState {
        val user by remember {
            userDetailsUseCase.fetchLoggedInUserDetailsFlow()
        }.collectAsState(initial = null)

        return if (user == null) {
            ProfileEditUiState.Loading
        } else {
            ProfileEditUiState.Content(profile = user!!) { event ->
                when(event) {
                    OnBack -> navigator.pop()
                    OnLogout -> TODO()
                    OnShowAskTheCommunity -> TODO()
                    OnShowDocuments -> TODO()
                    OnShowFaq -> TODO()
                    OnShowPrivacyPolicy -> TODO()
                    OnShowTermsAndConditions -> TODO()
                    is OnConnectWallet -> TODO()
                    is OnSaveProfile -> TODO()
                }
            }
        }
    }
}
