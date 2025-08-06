package io.newm.screens.investment.portfolio

import com.slack.circuit.runtime.CircuitUiState
import io.newm.shared.commonPublic.models.NFTTrack

sealed class InvestmentPortfolioState : CircuitUiState {
    data class Content(
        val claimableTokenAmount: Long,
        val streamTokens: List<NFTTrack>,
        val eventSink: (InvestmentPortfolioEvent) -> Unit
    ) : InvestmentPortfolioState()

    data object Loading : InvestmentPortfolioState()

    data object Error : InvestmentPortfolioState()

    data object ZeroState : InvestmentPortfolioState()
}