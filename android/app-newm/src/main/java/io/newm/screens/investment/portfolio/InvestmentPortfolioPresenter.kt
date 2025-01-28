package io.newm.screens.investment.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.public.usecases.HasWalletConnectionsUseCase
import io.newm.shared.public.usecases.SyncWalletConnectionsUseCase
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.flowOf

class InvestmentPortfolioPresenter(
    private val navigator: Navigator,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase,
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase,
    ) : Presenter<InvestmentPortfolioState> {
    @Composable
    override fun present(): InvestmentPortfolioState {

        LaunchedEffect(Unit) {
            syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
        }

        val isWalletConnected: Boolean? by remember { hasWalletConnectionsUseCase.hasWalletConnectionsFlow() }.collectAsRetainedState(
            null
        )
        val isWalletSynced by remember { walletNFTTracksUseCase.walletSynced }.collectAsState(
            false
        )

        val streamTokes by remember(isWalletConnected) {
            if (isWalletConnected == true) {
                walletNFTTracksUseCase.getAllStreamTokensFlow()
            } else {
                flowOf()
            }
        }.collectAsRetainedState(initial = emptyList())

        return when {
            streamTokes.isNotEmpty() -> {
                InvestmentPortfolioState.Content(
                    streamTokens = streamTokes,
                    eventSink = { event ->
                        when (event) {
                            InvestmentPortfolioEvent.OnBack -> {
                                navigator.pop()
                            }
                        }
                    }
                )
            }
            isWalletConnected == true && isWalletSynced -> {
                InvestmentPortfolioState.ZeroState
            }
            else -> {
                InvestmentPortfolioState.Error
            }
        }
    }
}