package io.newm.screens.profile.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.internal.rememberStableCoroutineScope
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.Logout
import io.newm.screens.Screen
import io.newm.screens.Screen.EditProfile
import io.newm.screens.Screen.PrivacyPolicy
import io.newm.screens.Screen.TermsOfService
import io.newm.screens.profile.OnBottomSheetVisible
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnDeveloperMenu
import io.newm.screens.profile.OnDisconnectWallet
import io.newm.screens.profile.OnEditProfile
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.screens.profile.OnVisitRecordStore
import io.newm.screens.profile.OnVisitStudio
import io.newm.screens.profile.OnWalletDialogOpened
import io.newm.screens.profile.OnWalletsScreen
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.featureflags.FeatureFlagService
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import io.newm.shared.commonPublic.usecases.ConnectWalletUseCase
import io.newm.shared.commonPublic.usecases.DisconnectWalletUseCase
import io.newm.shared.commonPublic.usecases.GetWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.HasWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.SyncWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.UserDetailsUseCase
import io.newm.sharedfeatures.devmenu.DevMenuMainScreen
import kotlinx.coroutines.launch

class ProfilePresenter(
    private val navigator: Navigator,
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase,
    private val getWalletConnectionsUseCase: GetWalletConnectionsUseCase,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
    private val disconnectWalletUseCase: DisconnectWalletUseCase,
    private val userDetailsUseCase: UserDetailsUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val featureFlagService: FeatureFlagService,
    private val logout: Logout,
    private val eventLogger: NewmAppEventLogger
) : Presenter<ProfileUiState> {

    @Composable
    override fun present(): ProfileUiState {
        val coroutineScope = rememberStableCoroutineScope()

        val isWalletConnected by remember {
            hasWalletConnectionsUseCase.hasWalletConnectionsFlow()
        }.collectAsState(false)

        val userConnectedWallets by remember {
            getWalletConnectionsUseCase.getWalletConnectionsFromCacheFlow()
        }.collectAsState(emptyList())

        LaunchedEffect(Unit) {
            syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
        }

        val user by remember {
            userDetailsUseCase.fetchLoggedInUserDetailsFlow()
        }.collectAsState(null)

        val showRecordStore by featureFlagService.observeFlag(FeatureFlags.ShowRecordStore)
            .collectAsState(initial = FeatureFlags.ShowRecordStore.defaultValue)

//        val showMultiWallets by featureFlagService.observeFlag(FeatureFlags.ShowMultiWallets)
//            .collectAsState(initial = FeatureFlags.ShowMultiWallets.defaultValue)
        val showMultiWallets = true

        val showStudio by featureFlagService.observeFlag(FeatureFlags.ShowNEWMStudio)
            .collectAsState(initial = FeatureFlags.ShowNEWMStudio.defaultValue)

        return if (user == null) {
            ProfileUiState.Loading
        } else {
            ProfileUiState.Content(
                profile = user!!,
                isWalletConnected = isWalletConnected,
                userConnectedWallets = userConnectedWallets,
                showRecordStore = showRecordStore,
                showMultiWallets = showMultiWallets,
                showStudio = showStudio,
                eventSink = { event ->
                    when (event) {
                        is OnConnectWallet -> coroutineScope.launch {
                            eventLogger.logClickEvent(AppScreens.AccountScreen.CONNECT_WALLET_BUTTON)
                            connectWalletUseCase.connect(event.newmCode)
                        }

                        OnDisconnectWallet -> coroutineScope.launch {
                            eventLogger.logClickEvent(AppScreens.AccountScreen.DISCONNECT_WALLET_BUTTON)
                            disconnectWalletUseCase.disconnect()
                        }

                        OnEditProfile -> {
                            eventLogger.logClickEvent(AppScreens.AccountScreen.EDIT_PROFILE_BUTTON)
                            navigator.goTo(EditProfile)
                        }

                        OnLogout -> {
                            eventLogger.logClickEvent(AppScreens.AccountScreen.LOGOUT_BUTTON)
                            logout.signOutUser()
                        }

                        OnShowTermsAndConditions -> {
                            eventLogger.logClickEvent(AppScreens.AccountScreen.TERMS_AND_CONDITIONS_BUTTON)
                            navigator.goTo(TermsOfService)
                        }

                        OnShowPrivacyPolicy -> {
                            eventLogger.logClickEvent(AppScreens.AccountScreen.PRIVACY_POLICY_BUTTON)
                            navigator.goTo(PrivacyPolicy)
                        }

                        OnWalletsScreen -> {
                            eventLogger.logClickEvent(AppScreens.AccountScreen.WALLETS_BUTTON)
                            navigator.goTo(Screen.Wallets)
                        }

                        OnBottomSheetVisible -> {
                            eventLogger.logClickEvent(AppScreens.AccountOptionsScreen.name)
                        }

                        OnVisitRecordStore -> {
                            eventLogger.logPageLoad(AppScreens.RecordStoreScreen.name)
                            eventLogger.logClickEvent(AppScreens.AccountScreen.VISIT_RECORDS_BUTTON)
                            navigator.goTo(Screen.RecordStore)
                        }

                        OnWalletDialogOpened -> {
                            eventLogger.logPageLoad(AppScreens.LogoutConfirmationDialogScreen.name)
                        }

                        OnVisitStudio -> {
                            navigator.goTo(Screen.Studio)
                            eventLogger.logClickEvent(AppScreens.AccountScreen.VISIT_STUDIO_BUTTON)
                        }

                        OnDeveloperMenu -> {
                            navigator.goTo(DevMenuMainScreen)
                        }
                    }
                }
            )
        }
    }
}