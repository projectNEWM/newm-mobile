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
import io.newm.screens.Screen
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.shared.commonPublic.usecases.ConnectWalletUseCase
import io.newm.shared.commonPublic.usecases.DisconnectWalletUseCase
import io.newm.shared.commonPublic.usecases.GetWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.HasWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.SyncWalletConnectionsUseCase
import kotlinx.coroutines.launch

class WalletsPresenter(
    private val navigator: Navigator,
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase,
    private val getWalletConnectionsUseCase: GetWalletConnectionsUseCase,
    private val disconnectWalletUseCase: DisconnectWalletUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
    private val eventLogger: NewmAppEventLogger,
) : Presenter<WalletsUiState> {
    @Composable
    override fun present(): WalletsUiState {
        /** In case user removes all of their wallets while on this screen, we show an empty state. */
        val userHasWalletsConnected: Boolean? by
            remember { hasWalletConnectionsUseCase.hasWalletConnectionsFlow() }
                .collectAsState(initial = null)

        val userConnectedWallets: List<WalletConnection>? by
            remember { getWalletConnectionsUseCase.getWalletConnectionsFromCacheFlow() }
                .collectAsState(initial = null)

        var isSyncing by remember { mutableStateOf(false) }
        val scope = rememberStableCoroutineScope()

        val eventSink: (WalletsEvent) -> Unit =
            remember {
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
                            scope.launch { disconnectWalletUseCase.disconnectSingleWallet(it.walletId) }
                        }

                        is WalletsEvent.OnConnectWallet -> {
                            scope.launch { connectWalletUseCase.connect(it.newmCode) }
                        }

                        is WalletsEvent.OnRenameWallet -> {
                            eventLogger.logClickEvent(AppScreens.WalletsScreen.WALLET_RENAME_CONFIRM)
                            // TODO logic to rename wallet
                        }

                        is WalletsEvent.OnWalletDetailView -> {
                            navigator.goTo(Screen.WalletDetail(it.walletId, "Wallet Name"))
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
                    isRefreshing = isSyncing,
                )
            }
        }
    }
}
