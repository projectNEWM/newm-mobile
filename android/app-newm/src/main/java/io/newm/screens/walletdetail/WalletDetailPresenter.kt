package io.newm.screens.walletdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.google.android.recaptcha.RecaptchaAction
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.screens.library.NFTLibraryState
import io.newm.shared.NewmAppLogger
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.shared.commonPublic.models.mocks.EmptyWallet
import io.newm.shared.commonPublic.usecases.FindWalletConnectionUseCase
import io.newm.shared.commonPublic.usecases.GetInvestmentPortfolioDataUseCase
import io.newm.shared.commonPublic.usecases.SyncWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.WalletNFTTracksUseCase
import io.newm.sharedfeatures.login.RecaptchaClientProvider
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class WalletDetailPresenter(
    private val navigator: Navigator,
    private val walletID: String,
    private val walletName: String,
    private val eventLogger: NewmAppEventLogger,
    private val logger: NewmAppLogger,
    private val findWalletConnectionUseCase: FindWalletConnectionUseCase,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
    private val nftTracksUseCase: WalletNFTTracksUseCase,
    private val getPortfolioDataUseCase: GetInvestmentPortfolioDataUseCase,
    private val recaptchaClientProvider: RecaptchaClientProvider,
) : Presenter<WalletDetailUiState> {
    @Composable
    override fun present(): WalletDetailUiState {
        /**
         * Grabbing the wallet connection from the cache, in this case [EmptyWallet] is the init value
         * so we're checking that for the loading state If we don't get a wallet from the cache, we
         * receive null - that's the error state because we should not be in this screen without a
         * wallet connection
         */
        val walletConnection: WalletConnection? by
            remember {
                try {
                    findWalletConnectionUseCase.findWalletConnectionByIDFromCacheFlow(walletID)
                } catch (t: Throwable) {
                    logger.error(TAG, "Failed to fetch wallet connection from cache", t)
                    flowOf(null)
                }
            }.collectAsState(initial = EmptyWallet)

        val nftTracks: List<NFTTrack> by
            remember { nftTracksUseCase.getAllCollectableTracksFlow() }
                .collectAsRetainedState(initial = emptyList())

        val streamTokens: List<NFTTrack> by
            remember { nftTracksUseCase.getAllStreamTokensFlow() }
                .collectAsRetainedState(initial = emptyList())

        val claimableTokenAmount by
            produceState(initialValue = 0L) {
                val connection = walletConnection
                if (connection != null) {
                    recaptchaClientProvider
                        .get()
                        .execute(RecaptchaAction.custom("get_earnings"))
                        .onSuccess { token ->
                            value =
                                getPortfolioDataUseCase.getInvestmentPortfolio(
                                    walletAddress = connection.stakeAddress,
                                    humanVerificationCode = token,
                                )
                        }.onFailure { logger.error(TAG, "Error getting recaptcha token", it) }
                }
            }

        var isSyncing by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        val eventSink: (WalletDetailEvent) -> Unit =
            remember {
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
            NFTLibraryState.EmptyWallet -> WalletDetailUiState.Loading(eventSink, isSyncing, walletName)

            null -> WalletDetailUiState.Error(eventSink, isSyncing, walletName)
            else ->
                WalletDetailUiState.Content(
                    eventSink,
                    isSyncing,
                    walletName,
                    requireNotNull(walletConnection) { "Wallet connection should not be null" },
                    nftTracks,
                    streamTokens,
                    claimableTokenAmount,
                )
        }
    }

    private companion object {
        const val TAG = "WalletDetailPresenter"
    }
}
