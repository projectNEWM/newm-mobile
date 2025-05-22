package io.newm.screens.studio

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.internal.TokenManager
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens

class StudioPresenter(
    private val navigator: Navigator,
    private val eventLogger: NewmAppEventLogger,
    private val tokenManager: TokenManager
) : Presenter<StudioState> {
    @Composable
    override fun present(): StudioState {
        val context = LocalContext.current
        val connectivityManager = remember {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
        val isNetworkAvailable by remember {
            mutableStateOf(connectivityManager.activeNetwork != null)
        }
        return when {
            !isNetworkAvailable -> StudioState.Error
            else -> {
                eventLogger.logPageLoad(AppScreens.MarketplaceScreen.name)
                StudioState.Content(
                    eventSink = {},
                    accessToken = tokenManager.getAccessToken(),
                    refreshToken = tokenManager.getRefreshToken()
                )
            }
        }
    }
}
