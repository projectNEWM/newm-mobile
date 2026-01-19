package io.newm.screens.recordstore

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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
        val context = LocalContext.current
        val connectivityManager =
            remember {
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            }
        val isNetworkAvailable by remember { mutableStateOf(connectivityManager.activeNetwork != null) }
        return when {
            !isNetworkAvailable -> RecordStoreState.Error
            else -> {
                eventLogger.logPageLoad(AppScreens.RecordStoreScreen.name)
                RecordStoreState.Content(eventSink = {})
            }
        }
    }
}
