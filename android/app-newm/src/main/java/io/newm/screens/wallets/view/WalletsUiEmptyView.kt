package io.newm.screens.wallets.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.newm.screens.wallets.WalletsEvent
import io.newm.screens.wallets.WalletsUiState
import io.newm.screens.wallets.util.rememberBarcodeScannerLauncher
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.sharedfeatures.theme.NewmTheme

@Composable
internal fun BoxScope.Empty(
    state: WalletsUiState.Empty,
    eventLogger: NewmAppEventLogger,
) {
    val launchBarcodeScanner =
        rememberBarcodeScannerLauncher {
            state.eventSink(WalletsEvent.OnConnectWallet(it))
        }

    // TODO: Get actual designs and copy for this view
    Card(
        modifier = Modifier.align(Alignment.Center).padding(vertical = 24.dp, horizontal = 24.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
        ) {
            Icon(
                modifier = Modifier.align(Alignment.CenterHorizontally).size(48.dp),
                imageVector = Icons.Default.Warning,
                contentDescription = null,
            )
            Text(text = "You don't have a wallet connected", style = MaterialTheme.typography.h4)
            Text(text = "Connect to access all your songs", style = MaterialTheme.typography.body2)
        }
    }

    ConnectNewWalletButton(
        modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
        onClick = {
            eventLogger.logClickEvent(AppScreens.WalletsScreen.EMPTY_ADD_WALLET_BUTTON)
            launchBarcodeScanner()
        },
    )
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_9_pro_xl")
@Composable
private fun Preview() {
    NewmTheme(darkTheme = true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Empty(state = WalletsUiState.Empty({}, false), eventLogger = NewmAppEventLogger())
        }
    }
}
