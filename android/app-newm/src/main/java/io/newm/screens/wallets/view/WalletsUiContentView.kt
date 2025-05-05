package io.newm.screens.wallets.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.newm.screens.wallets.WalletsEvent
import io.newm.screens.wallets.WalletsUiState
import io.newm.screens.wallets.util.rememberBarcodeScannerLauncher
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens

@Composable
internal fun BoxScope.Content(
    state: WalletsUiState.Content,
    eventLogger: NewmAppEventLogger,
    onOptionsClick: (String) -> Unit,
    onDisconnectAllClick: () -> Unit
) {
    val launchBarcodeScanner = rememberBarcodeScannerLauncher {
        state.eventSink(WalletsEvent.OnConnectWallet(it))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = state.wallets,
                key = { it.id }
            ) {
                WalletRow(
                    connection = it,
                    onOptionsClick = {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.WALLET_OPTIONS_BUTTON)
                        onOptionsClick(it)
                    }
                )
            }
        }
        DisconnectAllWalletsButton(onClick = onDisconnectAllClick)
        ConnectNewWalletButton(
            onClick = {
                eventLogger.logClickEvent(AppScreens.WalletsScreen.CONTENT_ADD_WALLET_BUTTON)
                launchBarcodeScanner()
            }
        )
    }
}