package io.newm.screens.marketplace

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.newm.core.resources.R
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.utils.ErrorScreen
import io.newm.core.ui.webview.FullScreenWebView
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens

private const val MARKETPLACE_URL = "https://marketplace.newm.io/"

@Composable
fun MarketplaceScreenUi(
    modifier: Modifier = Modifier,
    state: MarketplaceState,
    eventLogger: NewmAppEventLogger,
) {
    Column(modifier = modifier.fillMaxSize().statusBarsPadding()) {
        when (state) {
            is MarketplaceState.Content -> {
                FullScreenWebView(url = MARKETPLACE_URL)
            }

            MarketplaceState.Loading -> {
                LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.LoadingScreen.name) }
                LoadingScreen()
            }

            MarketplaceState.Error -> {
                ErrorScreen(
                    title = stringResource(R.string.record_store_error_title),
                    message = stringResource(R.string.record_store_error_message),
                )
            }
        }
    }
}
