package io.newm.screens.wallets.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.Gray23
import io.newm.core.theme.Gray6F
import io.newm.core.theme.inter
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.text.formLabelStyle
import io.newm.screens.wallets.WalletsEvent
import io.newm.screens.wallets.WalletsUiState
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.models.WalletConnection
import kotlinx.coroutines.launch
import java.util.Locale

enum class BottomSheetType {
    DISCONNECT,
    RENAME,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletsBottomSheetLayout(
    modifier: Modifier = Modifier,
    state: ModalBottomSheetState,
    type: BottomSheetType = BottomSheetType.DISCONNECT,
    selectedWalletConnection: WalletConnection?,
    walletState: WalletsUiState,
    eventLogger: NewmAppEventLogger,
    content: @Composable () -> Unit,
) {
    val screenName =
        remember(type) {
            if (type == BottomSheetType.DISCONNECT) {
                AppScreens.WalletDisconnectScreen.name
            } else {
                AppScreens.WalletRenameScreen.name
            }
        }

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(state.isVisible) {
        if (!state.isVisible) {
            keyboardController?.hide()
        }
    }

    ModalBottomSheetLayout(
        modifier = modifier.then(Modifier.imePadding()),
        sheetState = state,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            if (state.isVisible) {
                LaunchedEffect(Unit) { eventLogger.logPageLoad(screenName) }
            }
            if (type == BottomSheetType.DISCONNECT) {
                DisconnectContent(
                    walletState = walletState,
                    state = state,
                    walletId = selectedWalletConnection?.id,
                )
            } else {
                RenameContent(
                    walletState = walletState,
                    state = state,
                    wallet = requireNotNull(selectedWalletConnection) { "selectedWallet should not be null" },
                )
            }
        },
        scrimColor = Gray23.copy(alpha = 0.7F),
        content = content,
    )
}

@Composable
private fun RenameContent(
    walletState: WalletsUiState,
    state: ModalBottomSheetState,
    wallet: WalletConnection,
) {
    val scope = rememberCoroutineScope()
    var newName by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f).padding(start = 16.dp),
                text = stringResource(id = R.string.wallets_screen_rename_modal_title),
                style = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 24.sp),
            )
            IconButton(onClick = { scope.launch { state.hide() } }) {
                Icon(imageVector = Icons.Default.Close, null)
            }
        }
        Row(
            modifier = Modifier.width(IntrinsicSize.Min).padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            WalletRowItemDetails(wallet)
            Spacer(modifier = Modifier.weight(2f))
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.wallets_rename_text_label).uppercase(Locale.getDefault()),
            style = formLabelStyle.copy(color = Gray6F),
        )
        WalletsTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            value = newName,
            onValueChange = { newName = it },
            onDone = {
                scope.launch {
                    walletState.eventSink(
                        WalletsEvent.OnRenameWallet(
                            walletId = requireNotNull(wallet.id) { "selectedWalletId should not be null" },
                            newName = newName,
                        ),
                    )
                    state.hide()
                    newName = ""
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.done),
            onClick = {
                scope.launch {
                    walletState.eventSink(
                        WalletsEvent.OnRenameWallet(
                            walletId = requireNotNull(wallet.id) { "selectedWalletId should not be null" },
                            newName = newName,
                        ),
                    )
                    state.hide()
                    newName = ""
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DisconnectContent(
    walletState: WalletsUiState,
    state: ModalBottomSheetState,
    walletId: String?,
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.background).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(id = R.string.wallets_screen_disconnect_wallet_modal_title),
            style = TextStyle(fontFamily = inter, fontWeight = FontWeight.Bold, fontSize = 24.sp),
        )
        Text(
            text = stringResource(R.string.wallets_screen_disconnect_wallet_modal_desc),
            style = TextStyle(fontFamily = inter, fontWeight = FontWeight.Normal, fontSize = 14.sp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButton(
            text = stringResource(id = R.string.profile_disconnect_wallet_button_label),
            onClick = {
                scope.launch {
                    walletState.eventSink(
                        WalletsEvent.OnDisconnectWallet(
                            requireNotNull(walletId) { "selectedWalletId should not be null" },
                        ),
                    )
                    state.hide()
                }
            },
        )
        SecondaryButton(
            labelResId = R.string.dialog_cancel,
            onClick = { scope.launch { state.hide() } },
        )
    }
}
