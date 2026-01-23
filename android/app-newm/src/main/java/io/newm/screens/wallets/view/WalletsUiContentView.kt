package io.newm.screens.wallets.view

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.newm.screens.wallets.WalletsEvent
import io.newm.screens.wallets.WalletsUiState
import io.newm.screens.wallets.util.rememberBarcodeScannerLauncher
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.sharedfeatures.core.resources.R
import kotlinx.coroutines.launch

@Composable
internal fun Content(
    state: WalletsUiState.Content,
    eventLogger: NewmAppEventLogger,
    onDisconnectWallet: (WalletConnection) -> Unit,
    onRenameWallet: (WalletConnection) -> Unit,
) {
    val launchBarcodeScanner =
        rememberBarcodeScannerLauncher {
            state.eventSink(WalletsEvent.OnConnectWallet(it))
        }

    val clipboard = LocalClipboard.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(items = state.wallets, key = { it.id }) {
                WalletRowItem(
                    connection = it,
                    eventLogger = eventLogger,
                    onViewDetailsClick = {
                        eventLogger.logClickEvent(
                            AppScreens.WalletsScreen.VIEW_DETAILS_WALLET_BUTTON,
                        )
                        state.eventSink(WalletsEvent.OnWalletDetailView(it.id))
                    },
                    onRenameClick = {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.RENAME_WALLET_BUTTON)
                        onRenameWallet(it)
                    },
                    onCopyAddressClick = {
                        eventLogger.logClickEvent(
                            AppScreens.WalletsScreen.COPY_ADDRESS_WALLET_BUTTON,
                        )
                        scope.launch {
                            clipboard.setClipEntry(
                                ClipEntry(
                                    ClipData.newPlainText(
                                        context.getString(
                                            R.string.wallets_copy_address_label,
                                            it.id, // TODO ID should be replaced with wallet name
                                        ),
                                        it.stakeAddress,
                                    ),
                                ),
                            )
                        }
                    },
                    onDisconnectClick = {
                        eventLogger.logClickEvent(AppScreens.WalletsScreen.DISCONNECT_WALLET_BUTTON)
                        onDisconnectWallet(it)
                    },
                )
            }
        }
        ConnectNewWalletButton(
            onClick = {
                eventLogger.logClickEvent(AppScreens.WalletsScreen.CONTENT_ADD_WALLET_BUTTON)
                launchBarcodeScanner()
            },
        )
    }
}
