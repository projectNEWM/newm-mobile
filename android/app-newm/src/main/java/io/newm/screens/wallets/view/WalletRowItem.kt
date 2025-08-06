package io.newm.screens.wallets.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.Gray16
import io.newm.core.theme.GraySuit
import io.newm.core.theme.inter
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.models.WalletConnection

@Composable
fun WalletRowItem(
    connection: WalletConnection,
    eventLogger: NewmAppEventLogger,
    onViewDetailsClick: () -> Unit,
    onRenameClick: () -> Unit,
    onCopyAddressClick: () -> Unit,
    onDisconnectClick: () -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        WalletRowItemDetails(connection)
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.wrapContentSize(Alignment.BottomEnd)) {
            IconButton(
                onClick = {
                    eventLogger.logClickEvent(AppScreens.WalletsScreen.WALLET_OPTIONS_BUTTON)
                    isDropdownExpanded = true
                },
            ) {
                Icon(
                    modifier = Modifier
                        .background(
                            color = Gray16,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    imageVector = Icons.Rounded.MoreVert,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.wallets_screen_wallet_options_desc)
                )
            }
            WalletDropDownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
                onViewDetailsClick = onViewDetailsClick,
                onRenameClick = onRenameClick,
                onCopyAddressClick = onCopyAddressClick,
                onDisconnectClick = onDisconnectClick
            )
        }
    }
}

@Composable
fun WalletRowItemDetails(connection: WalletConnection) {
    Icon(
        imageVector = Icons.Rounded.Info,
        contentDescription = "Wallet image placeholder"
    )
    Column(modifier = Modifier.width(150.dp)) {
        Text(
            text = connection.id,
            maxLines = 1,
            overflow = TextOverflow.MiddleEllipsis,
            style = TextStyle(
                color = MaterialTheme.colors.onBackground,
                fontSize = 14.sp,
                fontFamily = inter,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = connection.stakeAddress,
            maxLines = 1,
            overflow = TextOverflow.MiddleEllipsis,
            style = TextStyle(
                color = GraySuit,
                fontSize = 12.sp,
                fontFamily = inter,
                fontWeight = FontWeight.Normal
            )
        )
    }
}