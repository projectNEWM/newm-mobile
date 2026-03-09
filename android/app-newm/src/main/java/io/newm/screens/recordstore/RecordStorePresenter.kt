package io.newm.screens.recordstore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens

class RecordStorePresenter(
    private val navigator: Navigator,
    private val eventLogger: NewmAppEventLogger,
) : Presenter<RecordStoreState> {
    @Composable
    override fun present(): RecordStoreState {
        LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.RecordStoreScreen.name) }
        return RecordStoreState.Content(eventSink = {})
    }
}
