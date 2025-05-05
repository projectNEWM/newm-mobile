package io.newm.screens.wallets.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.newm.core.resources.R
import io.newm.core.ui.buttons.NewmButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.screens.profile.view.defaultButtonLabelStyle

@Composable
fun ConnectNewWalletButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    NewmButton(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.wallets_screen_connect_new_wallet),
            style = defaultButtonLabelStyle
        )
    }
}

@Composable
fun DisconnectAllWalletsButton(onClick: () -> Unit) {
    SecondaryButton(
        label = stringResource(R.string.wallets_screen_disconnect_all_wallets),
        textStyle = defaultButtonLabelStyle,
        onClick = onClick
    )
}