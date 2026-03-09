package io.newm.screens.studio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.shared.commonInternal.TokenManager
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens

class StudioPresenter(
    private val navigator: Navigator,
    private val eventLogger: NewmAppEventLogger,
    private val tokenManager: TokenManager,
) : Presenter<StudioState> {
    @Composable
    override fun present(): StudioState {
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
            isLoading -> {
                StudioState.Loading
            }

            else -> {
                LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.StudioScreen.name) }
                StudioState.Content(
                    eventSink = {},
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                )
            }
        }
    }
}
