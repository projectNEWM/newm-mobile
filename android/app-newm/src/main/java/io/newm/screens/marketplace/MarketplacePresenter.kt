package io.newm.screens.marketplace

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens

class MarketplacePresenter(
    private val navigator: Navigator,
    private val eventLogger: NewmAppEventLogger,
) : Presenter<MarketplaceState> {
    @Composable
    override fun present(): MarketplaceState {
        LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.MarketplaceScreen.name) }
        return MarketplaceState.Content(eventSink = {})
    }
}
