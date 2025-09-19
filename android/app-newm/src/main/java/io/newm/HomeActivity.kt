package io.newm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.retained.LocalRetainedStateRegistry
import com.slack.circuit.retained.continuityRetainedStateRegistry
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import io.newm.core.theme.NewmTheme
import io.newm.screens.Screen
import io.newm.screens.Screen.NFTLibrary
import io.newm.screens.forceupdate.ForceAppUpdatePresenter
import io.newm.screens.forceupdate.ForceAppUpdateState
import io.newm.screens.forceupdate.ForceAppUpdateUi
import io.newm.screens.forceupdate.openAppPlayStore
import io.newm.screens.investment.portfolio.InvestmentPortfolioPresenter
import io.newm.screens.investment.portfolio.InvestmentPortfolioState
import io.newm.screens.investment.portfolio.InvestmentPortfolioUi
import io.newm.screens.library.NFTLibraryPresenter
import io.newm.screens.library.NFTLibraryScreenUi
import io.newm.screens.library.NFTLibraryState
import io.newm.screens.marketplace.MarketplacePresenter
import io.newm.screens.marketplace.MarketplaceScreenUi
import io.newm.screens.marketplace.MarketplaceState
import io.newm.screens.profile.edit.ProfileEditPresenter
import io.newm.screens.profile.edit.ProfileEditUi
import io.newm.screens.profile.edit.ProfileEditUiState
import io.newm.screens.profile.view.ProfilePresenter
import io.newm.screens.profile.view.ProfileUi
import io.newm.screens.profile.view.ProfileUiState
import io.newm.screens.recordstore.RecordStorePresenter
import io.newm.screens.recordstore.RecordStoreScreenUi
import io.newm.screens.recordstore.RecordStoreState
import io.newm.screens.studio.StudioPresenter
import io.newm.screens.studio.StudioScreenUi
import io.newm.screens.studio.StudioState
import io.newm.screens.walletdetail.WalletDetailPresenter
import io.newm.screens.walletdetail.WalletDetailUiState
import io.newm.screens.walletdetail.view.WalletDetailUi
import io.newm.screens.wallets.WalletsPresenter
import io.newm.screens.wallets.WalletsUiState
import io.newm.screens.wallets.view.WalletsUi
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.featureflags.FeatureFlagService
import io.newm.shared.commonPublic.featureflags.FeatureFlags
import io.newm.sharedfeatures.devmenu.DevMenuMainScreen
import io.newm.sharedfeatures.devmenu.DevMenuPresenter
import io.newm.sharedfeatures.devmenu.DevMenuUi
import io.newm.sharedfeatures.devmenu.FeatureFlagsListPresenter
import io.newm.sharedfeatures.devmenu.FeatureFlagsListScreen
import io.newm.sharedfeatures.devmenu.FeatureFlagsListUi
import io.newm.utils.DynamicStatusBarSideEffect
import io.newm.utils.ForceAppUpdateViewModel
import io.newm.utils.ui
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : ComponentActivity() {
    private val circuit: Circuit = createCircuit()
    private val logger: NewmAppLogger by inject()
    private val forceAppUpdateViewModel: ForceAppUpdateViewModel by inject()
    private val eventLogger: NewmAppEventLogger by inject()
    private val featureFlagService: FeatureFlagService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            NewmTheme(darkTheme = true) {
                DynamicStatusBarSideEffect(darkTheme = true)
                CircuitDependencies {
                    val updateRequired by forceAppUpdateViewModel.updateRequiredState.collectAsState()
                    if (updateRequired) {
                        ForceAppUpdateUi(
                            state = ForceAppUpdateState.Content(eventSink = {
                                eventLogger.logClickEvent(AppScreens.ForceUpdateScreen.UPDATE_BUTTON)
                                openAppPlayStore()
                            }),
                            eventLogger
                        )
                    } else {
                        val showRecordStore by featureFlagService.observeFlag(FeatureFlags.ShowRecordStore)
                            .collectAsState(initial = FeatureFlags.ShowRecordStore.defaultValue)

                        val showInvestmentPortfolio by featureFlagService.observeFlag(FeatureFlags.ShowInvestmentPortfolio)
                            .collectAsState(initial = FeatureFlags.ShowInvestmentPortfolio.defaultValue)

                        NewmApp(
                            logger = logger,
                            eventLogger = eventLogger,
                            showRecordStore = showRecordStore,
                            showInvestmentPortfolio = showInvestmentPortfolio
                        )
                    }
                }
            }
        }
    }

    private fun createCircuit(): Circuit {
        return Circuit.Builder()
            .addPresenterFactory(buildPresenterFactory())
            .addUiFactory(buildUiFactory())
            .build()
    }

    private fun buildUiFactory(): Ui.Factory {
        return Ui.Factory { screen, _ ->
            when (screen) {
                is Screen.UserAccount -> ui<ProfileUiState> { state, modifier ->
                    ProfileUi(
                        state = state,
                        modifier = modifier
                    )
                }

                is Screen.RecordStore -> ui<RecordStoreState> { state, modifier ->
                    RecordStoreScreenUi(
                        state = state,
                        modifier = modifier,
                        eventLogger = eventLogger
                    )
                }

                is Screen.Marketplace -> ui<MarketplaceState> { state, modifier ->
                    MarketplaceScreenUi(
                        state = state,
                        modifier = modifier,
                        eventLogger = eventLogger
                    )
                }

                is NFTLibrary -> ui<NFTLibraryState> { state, modifier ->
                    NFTLibraryScreenUi(
                        state = state,
                        modifier = modifier,
                        eventLogger = eventLogger
                    )
                }

                is Screen.EditProfile -> ui<ProfileEditUiState> { state, modifier ->
                    ProfileEditUi(
                        modifier = modifier,
                        state = state
                    )
                }

                is Screen.ForceAppUpdate -> ui<ForceAppUpdateState> { state, modifier ->
                    ForceAppUpdateUi(
                        state = state,
                        modifier = modifier,
                        eventLogger = eventLogger
                    )
                }

                is Screen.InvestmentPortfolio -> ui<InvestmentPortfolioState> { state, modifier ->
                    InvestmentPortfolioUi(
                        state = state,
                        modifier = modifier,
                        eventLogger = eventLogger
                    )
                }

                is Screen.Wallets -> ui<WalletsUiState> { state, modifier ->
                    WalletsUi(
                        state = state,
                        modifier = modifier,
                        eventLogger = eventLogger
                    )
                }

                is Screen.WalletDetail -> ui<WalletDetailUiState> { state, modifier ->
                    WalletDetailUi(
                        state = state,
                        modifier = modifier
                    )
                }

                is Screen.Studio -> ui<StudioState> { state, modifier ->
                    StudioScreenUi(
                        state = state,
                        modifier = modifier,
                        eventLogger = eventLogger
                    )
                }

                is DevMenuMainScreen -> ui<DevMenuMainScreen.UiState> { state, modifier ->
                    DevMenuUi(state, modifier)
                }

                is FeatureFlagsListScreen -> ui<FeatureFlagsListScreen.UiState> { state, modifier ->
                    FeatureFlagsListUi(state, modifier)
                }

                else -> null

            }
        }
    }

    private fun buildPresenterFactory(): Presenter.Factory {
        return Presenter.Factory { screen, navigator, _ ->
            when (screen) {
                is Screen.UserAccount -> inject<ProfilePresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is NFTLibrary -> inject<NFTLibraryPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is Screen.RecordStore -> inject<RecordStorePresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is Screen.Marketplace -> inject<MarketplacePresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is Screen.EditProfile -> inject<ProfileEditPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is Screen.ForceAppUpdate -> inject<ForceAppUpdatePresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is Screen.InvestmentPortfolio -> inject<InvestmentPortfolioPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is Screen.Wallets -> inject<WalletsPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is Screen.WalletDetail -> inject<WalletDetailPresenter> {
                    parametersOf(
                        navigator,
                        screen.walletId,
                        screen.walletName
                    )
                }.value

                is Screen.Studio -> inject<StudioPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                is DevMenuMainScreen -> inject<DevMenuPresenter> { parametersOf(navigator) }.value
                is FeatureFlagsListScreen -> inject<FeatureFlagsListPresenter> {
                    parametersOf(
                        navigator
                    )
                }.value

                else -> null
            }
        }
    }

    @Composable
    fun CircuitDependencies(
        content: @Composable () -> Unit
    ) {
        CircuitCompositionLocals(circuit) {
            CompositionLocalProvider(
                LocalRetainedStateRegistry provides continuityRetainedStateRegistry(),
                LocalIsBottomBarVisible provides isBottomBarVisible()
            ) {
                content()
            }
        }
    }
}