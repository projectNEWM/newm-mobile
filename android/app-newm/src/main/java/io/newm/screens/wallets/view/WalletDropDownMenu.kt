package io.newm.screens.wallets.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.Gray500

@Composable
internal fun WalletDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onViewDetailsClick: () -> Unit,
    onRenameClick: () -> Unit,
    onCopyAddressClick: () -> Unit,
    onDisconnectClick: () -> Unit,
) {
    val menuItems =
        listOf(
            MenuItemData(
                icon = Icons.Rounded.RemoveRedEye,
                labelResId = R.string.wallet_menu_details,
                onClick = {
                    onDismissRequest()
                    onViewDetailsClick()
                },
            ),
            MenuItemData(
                icon = Icons.Rounded.Edit,
                labelResId = R.string.wallet_menu_rename,
                onClick = {
                    onDismissRequest()
                    onRenameClick()
                },
            ),
            MenuItemData(
                icon = Icons.Filled.ContentCopy,
                labelResId = R.string.wallet_menu_copy,
                onClick = {
                    onDismissRequest()
                    onCopyAddressClick()
                },
            ),
            MenuItemData(
                icon = Icons.Rounded.Close,
                labelResId = R.string.wallet_menu_disconnect,
                onClick = {
                    onDismissRequest()
                    onDisconnectClick()
                },
            ),
        )
    DropdownMenu(
        modifier = Modifier.background(color = Gray500).width(IntrinsicSize.Min),
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        offset = DpOffset(x = (-4).dp, y = (-2).dp),
    ) {
        menuItems.forEach {
            DropdownMenuItem(onClick = it.onClick) {
                WalletDropDownItem(image = it.icon, text = stringResource(id = it.labelResId))
            }
        }
    }
}

@Composable
private fun WalletDropDownItem(
    image: ImageVector,
    text: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(image, null)
        Text(text = text)
    }
}

private data class MenuItemData(
    val icon: ImageVector,
    val labelResId: Int,
    val onClick: () -> Unit,
)
