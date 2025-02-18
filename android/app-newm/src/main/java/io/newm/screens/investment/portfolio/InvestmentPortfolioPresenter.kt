package io.newm.screens.investment.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.android.recaptcha.RecaptchaAction
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.login.screen.authproviders.RecaptchaClientProvider
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.usecases.GetInvestmentPortfolioDataUseCase
import io.newm.shared.public.usecases.HasWalletConnectionsUseCase
import io.newm.shared.public.usecases.SyncWalletConnectionsUseCase
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class InvestmentPortfolioPresenter(
    private val navigator: Navigator,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase,
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase,
    private val getPortfolioDataUseCase: GetInvestmentPortfolioDataUseCase,
    private val recaptchaClientProvider: RecaptchaClientProvider,
    private val logger: NewmAppLogger,
) : Presenter<InvestmentPortfolioState> {
    @Composable
    override fun present(): InvestmentPortfolioState {
        val coroutineScope = rememberCoroutineScope()

        // State to hold claimable token amount
        var claimableTokenAmount by remember { mutableStateOf(0L) }

        LaunchedEffect(Unit) {
            val wallets = syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
            if (wallets.isNotEmpty()) {
                coroutineScope.launch {
                    recaptchaClientProvider.get()
                        .execute(RecaptchaAction.custom("get_earnings"))
                        .onSuccess { token ->
                            val totalClaimableTokens =
                                getPortfolioDataUseCase.getInvestmentPortfolio(
                                    walletAddress = wallets.first().stakeAddress,
                                    humanVerificationCode = token
                                )
                            // Store value in state
                            claimableTokenAmount = totalClaimableTokens
                        }.onFailure {
                            logger.error(
                                "InvestmentPortfolioPresenter",
                                "Error getting recaptcha token",
                                it
                            )
                        }
                }
            }
        }

        val isWalletConnected: Boolean? by remember { hasWalletConnectionsUseCase.hasWalletConnectionsFlow() }
            .collectAsRetainedState(null)

        val isWalletSynced by remember { walletNFTTracksUseCase.walletSynced }
            .collectAsState(false)

        val streamTokens by remember(isWalletConnected) {
            if (isWalletConnected == true) {
                walletNFTTracksUseCase.getAllStreamTokensFlow()
            } else {
                flowOf(emptyList())
            }
        }.collectAsRetainedState(initial = emptyList())

        return when {
            streamTokens.isNotEmpty() -> {
                InvestmentPortfolioState.Content(
                    claimableTokenAmount = claimableTokenAmount, // Use stored claimable token amount
                    streamTokens = streamTokens,
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