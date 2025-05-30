package io.newm.screens.walletdetail

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
import io.newm.shared.public.models.mocks.EmptyWallet
import io.newm.shared.public.usecases.FindWalletConnectionUseCase
import io.newm.shared.public.usecases.SyncWalletConnectionsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class WalletDetailPresenter(
    private val navigator: Navigator,
    private val walletID: String,
    private val walletName: String,
    private val eventLogger: NewmAppEventLogger,
    private val findWalletConnectionUseCase: FindWalletConnectionUseCase,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
) : Presenter<WalletDetailUiState> {
    @Composable
    override fun present(): WalletDetailUiState {
        /**
         * Grabbing the wallet connection from the cache, in this case
         * [EmptyWallet] is the init value so we're checking that for the loading state
         * If we don't get a wallet from the cache, we receive null - so that's the error state
         * because we should not be in this screen without a wallet connection
         */
        val walletConnection: WalletConnection? by remember {
            try {
                findWalletConnectionUseCase.findWalletConnectionByIDFromCacheFlow(walletID)
            } catch (_: Throwable) {
                flowOf(null)
            }
        }.collectAsState(initial = EmptyWallet)

        var isSyncing by remember { mutableStateOf(false) }
        val scope = rememberStableCoroutineScope()

        val eventSink: (WalletDetailEvent) -> Unit = remember {
            {
                when (it) {
                    WalletDetailEvent.OnBack -> {
                        eventLogger.logEvent(AppScreens.WalletDetailScreen.BACK_BUTTON)
                        navigator.pop()
                    }

                    WalletDetailEvent.OnRefresh -> {
                        eventLogger.logEvent(AppScreens.WalletDetailScreen.PULL_TO_REFRESH)
                        scope.launch {
                            isSyncing = true
                            syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
                            isSyncing = false
                        }
                    }
                }
            }
        }

        return when (walletConnection) {
            EmptyWallet -> WalletDetailUiState.Loading(eventSink, isSyncing, walletName)
            null -> WalletDetailUiState.Error(eventSink, isSyncing, walletName)
            else -> WalletDetailUiState.Content(
                eventSink,
                isSyncing,
                walletName,
                requireNotNull(walletConnection) { "Wallet connection should not be null" }
            )
        }
    }

    private companion object {
        const val TAG = "WalletDetailPresenter"
    }
}