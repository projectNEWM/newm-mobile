package io.newm.screens.investment.portfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
        // State to hold claimable token amount using produceState
        val claimableTokenAmount by produceState(initialValue = 0L) {
            val wallets = syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
            if (wallets.isNotEmpty()) {
                recaptchaClientProvider.get()
                    .execute(RecaptchaAction.custom("get_earnings"))
                    .onSuccess { token ->
                        value = getPortfolioDataUseCase.getInvestmentPortfolio(
                            walletAddress = wallets.first().stakeAddress,
                            humanVerificationCode = token
                        )
                    }.onFailure {
                        logger.error("InvestmentPortfolioPresenter", "Error getting recaptcha token", it)
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