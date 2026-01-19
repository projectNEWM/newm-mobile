import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.screens.profile.edit.ScrimCircle
import io.newm.shared.commonPublic.models.WalletConnection

@Composable
fun WalletRow(
    connection: WalletConnection,
    onOptionsClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.Rounded.Info, contentDescription = "Wallet image placeholder")
        Column(modifier = Modifier.weight(1f)) {
            Text(text = connection.id, maxLines = 1, overflow = TextOverflow.MiddleEllipsis)
            Text(text = connection.stakeAddress, maxLines = 1, overflow = TextOverflow.MiddleEllipsis)
        }
        Spacer(modifier = Modifier.weight(1f))
        ScrimCircle {
            IconButton(onClick = { onOptionsClick(connection.id) }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(id = R.string.wallets_screen_wallet_options_desc),
                )
            }
        }
    }
}
