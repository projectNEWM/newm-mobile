package io.newm.screens.wallets.view

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.screens.profile.view.defaultButtonLabelStyle
import io.newm.sharedfeatures.core.resources.R
import io.newm.sharedfeatures.theme.DarkViolet
import io.newm.sharedfeatures.theme.Pinkish
import io.newm.sharedfeatures.theme.Purple
import io.newm.sharedfeatures.ui.buttons.NewmButton
import io.newm.sharedfeatures.ui.utils.iconGradient

private val walletButtonGradient =
    iconGradient(DarkViolet.copy(alpha = 0.08f), Pinkish.copy(alpha = 0.08f))

@Composable
fun ConnectNewWalletButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    NewmButton(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min).then(modifier),
        unselectedBrush = walletButtonGradient,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically).fillMaxHeight(),
            imageVector = Icons.Sharp.Add,
            tint = Purple,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = stringResource(R.string.wallets_screen_connect_new_wallet),
            style = defaultButtonLabelStyle,
        )
    }
}
