package io.newm.screens.investment.portfolio

import com.slack.circuit.runtime.CircuitUiEvent


sealed interface InvestmentPortfolioEvent : CircuitUiEvent {
    data object OnBack : InvestmentPortfolioEvent
}