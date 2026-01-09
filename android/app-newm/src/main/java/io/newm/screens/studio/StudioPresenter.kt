package io.newm.screens.studio

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens

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

        var accessToken by remember { mutableStateOf<String?>(null) }
        var refreshToken by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(true) }

        // Load tokens asynchronously
        LaunchedEffect(Unit) {
            accessToken = tokenManager.getAccessToken()
            refreshToken = tokenManager.getRefreshToken()
            isLoading = false
        }

        return when {
            isLoading -> StudioState.Loading
            !isNetworkAvailable -> StudioState.Error
            else -> {
                eventLogger.logPageLoad(AppScreens.MarketplaceScreen.name)
                StudioState.Content(
                    eventSink = {},
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
            }
        }
    }
}
