package io.newm.screens.wallets.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.Black90
import io.newm.core.theme.CerisePink
import io.newm.core.theme.Gray400
import io.newm.core.theme.SteelPink
import io.newm.core.theme.raleway
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.utils.textGradient
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalletsBottomSheetLayout(
    modifier: Modifier = Modifier,
    state: ModalBottomSheetState,
    eventLogger: NewmAppEventLogger,
    onDisconnectWallet: () -> Unit,
    onCancel: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = state,
        sheetContent = {
            if (state.isVisible) {
                LaunchedEffect(Unit) {
                    eventLogger.logPageLoad(AppScreens.WalletOptionsScreen.name)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colors.surface,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Divider(
                    thickness = 1.dp,
                    color = Gray400
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.wallets_screen_disconnect_wallet_modal_title),
                        style = TextStyle(
                            fontFamily = raleway,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            brush = textGradient(SteelPink, CerisePink)
                        )
                    )
                    SecondaryButton(
                        labelResId = R.string.dialog_cancel,
                        onClick = onCancel
                    )
                    PrimaryButton(
                        text = stringResource(id = R.string.profile_disconnect_wallet_button_label),
                        onClick = onDisconnectWallet
                    )
                }
            }
        },
        scrimColor = Black90,
        content = content
    )
}