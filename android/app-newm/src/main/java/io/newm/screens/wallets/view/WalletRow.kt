package io.newm.screens.wallets.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.shared.public.models.WalletConnection

@Composable
fun WalletRow(
    connection: WalletConnection,
    onOptionsClick: () -> Unit
) {
    val halfScreenWidth = LocalConfiguration.current.screenWidthDp.dp / 2
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Wallet image placeholder"
        )
        Column(modifier = Modifier.width(halfScreenWidth)) {
            // TextOverflow.MiddleEllipsis is coming in foundation 1.8.0
            Text(
                text = connection.id,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = connection.stakeAddress,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ScrimCircle {
            IconButton(
                onClick = onOptionsClick,
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(id = R.string.wallets_screen_wallet_options_desc)
                )
            }
        }
    }
}