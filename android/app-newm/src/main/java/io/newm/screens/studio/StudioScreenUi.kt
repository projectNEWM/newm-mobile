package io.newm.screens.studio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.newm.core.resources.R
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.utils.ErrorScreen
import io.newm.core.ui.webview.FullScreenWebView
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens

private const val STUDIO_URL = "https://newm.studio/home/library"

@Composable
fun StudioScreenUi(
    modifier: Modifier = Modifier,
    state: StudioState,
    eventLogger: NewmAppEventLogger
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        when (state) {
            is StudioState.Content -> {
                FullScreenWebView(
                    context = context,
                    url = STUDIO_URL,
                    accessToken = state.accessToken,
                    refreshToken = state.refreshToken
                )
            }

            StudioState.Loading -> {
                LaunchedEffect(Unit) {
                    eventLogger.logPageLoad(AppScreens.LoadingScreen.name)
                }
                LoadingScreen()
            }

            StudioState.Error -> {
                ErrorScreen(
                    title = stringResource(R.string.record_store_error_title),
                    message = stringResource(R.string.record_store_error_message)
                )
            }
        }
    }
}

