package io.newm.screens.marketplace

import com.slack.circuit.runtime.CircuitUiState


sealed class MarketplaceState : CircuitUiState {
    data object Loading : MarketplaceState()
    data class Content(
        val eventSink: (MarketplaceUiEvent) -> Unit,
    ) : MarketplaceState()
    data object Error : MarketplaceState()
}
