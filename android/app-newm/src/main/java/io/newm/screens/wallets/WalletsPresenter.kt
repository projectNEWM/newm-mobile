package io.newm.screens.wallets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.internal.rememberStableCoroutineScope
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.DisconnectWalletUseCase
import io.newm.shared.public.usecases.GetWalletConnectionsUseCase
import io.newm.shared.public.usecases.HasWalletConnectionsUseCase
import io.newm.shared.public.usecases.SyncWalletConnectionsUseCase
import kotlinx.coroutines.launch

class WalletsPresenter(
    private val navigator: Navigator,
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase,
    private val getWalletConnectionsUseCase: GetWalletConnectionsUseCase,
    private val disconnectWalletUseCase: DisconnectWalletUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
    private val eventLogger: NewmAppEventLogger
) : Presenter<WalletsUiState> {
    @Composable
    override fun present(): WalletsUiState {
        /**
         * In case user removes all of their wallets while on this screen,
         * we show an empty state.
         */
        val userHasWalletsConnected: Boolean? by remember {
            hasWalletConnectionsUseCase.hasWalletConnectionsFlow()
        }.collectAsState(initial = null)

        val userConnectedWallets: List<WalletConnection>? by remember {
            getWalletConnectionsUseCase.getWalletConnectionsFromCacheFlow()
        }.collectAsState(initial = null)

        var isSyncing by remember { mutableStateOf(false) }
        val scope = rememberStableCoroutineScope()

        val eventSink: (WalletsEvent) -> Unit = remember {
            {
                when (it) {
                    is WalletsEvent.OnBack -> {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.BACK_BUTTON)
                        navigator.pop()
                    }

                    is WalletsEvent.OnRefresh -> {
                        eventLogger.logEvent(AppScreens.WalletsScreen.PULL_TO_REFRESH)
                        scope.launch {
                            isSyncing = true
                            syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
                            isSyncing = false
                        }
                    }

                    is WalletsEvent.OnDisconnectWallet -> {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.DISCONNECT_WALLET_BUTTON)
                        scope.launch {
                            disconnectWalletUseCase.disconnectSingleWallet(it.walletId)
                        }
                    }

                    is WalletsEvent.OnDisconnectAllWallets -> {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.DISCONNECT_ALL_WALLETS_BUTTON)
                        scope.launch {
                            disconnectWalletUseCase.disconnect()
                        }
                    }

                    is WalletsEvent.OnConnectWallet -> {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.ADD_WALLET_BUTTON)
                        scope.launch {
                            connectWalletUseCase.connect(it.newmCode)
                        }
                    }
                }
            }
        }

        return when {
            userConnectedWallets == null || userHasWalletsConnected == null -> {
                WalletsUiState.Loading(eventSink = eventSink, isRefreshing = isSyncing)
            }

            userConnectedWallets?.isEmpty() == true && userHasWalletsConnected == false -> {
                WalletsUiState.Empty(eventSink = eventSink, isRefreshing = isSyncing)
            }

            else -> {
                WalletsUiState.Content(
                    wallets = requireNotNull(userConnectedWallets) { "Wallets should not be null!" },
                    eventSink = eventSink,
                    isRefreshing = isSyncing
                )
            }
        }
    }
}