package io.newm.screens.profile.view

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.DarkViolet
import io.newm.core.theme.LightSkyBlue
import io.newm.core.theme.OceanGreen
import io.newm.core.theme.Pinkish
import io.newm.core.theme.Purple
import io.newm.core.theme.inter
import io.newm.core.ui.ConfirmationDialog
import io.newm.core.ui.buttons.NewmButton
import io.newm.core.ui.permissions.AppPermission
import io.newm.core.ui.permissions.doWithPermission
import io.newm.core.ui.permissions.rememberRequestPermissionIntent
import io.newm.core.ui.utils.drawWithBrush
import io.newm.core.ui.utils.iconGradient
import io.newm.core.ui.utils.shortToast
import io.newm.feature.barcode.scanner.BarcodeScannerActivity
import io.newm.shared.public.analytics.NewmAppEventLogger

private val defaultProfileButtonGradient =
    iconGradient(DarkViolet.copy(alpha = 0.08f), Pinkish.copy(alpha = 0.08f))

private val disconnectWalletButtonGradient =
    iconGradient(OceanGreen.copy(alpha = 0.08f), LightSkyBlue.copy(alpha = 0.08f))

private val disconnectWalletButtonTextGradient =
    iconGradient(OceanGreen, LightSkyBlue)

private val defaultButtonLabelStyle = TextStyle(
    fontSize = 14.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Medium,
    color = Purple
)

private val disconnectButtonLabelStyle = TextStyle(
    fontSize = 14.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Medium,
    color = LightSkyBlue
)

@Composable
fun ProfileButton(
    modifier: Modifier = Modifier,
    label: String,
    backgroundBrush: Brush = defaultProfileButtonGradient,
    textStyle: TextStyle = defaultButtonLabelStyle,
    onClick: () -> Unit = {}
) {
    NewmButton(
        modifier = modifier.fillMaxWidth(),
        unselectedBrush = backgroundBrush,
        onClick = onClick
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }
}

@Composable
fun WalletsButton(
    isWalletConnected: Boolean = false,
    onConnectWalletClick: (String) -> Unit = {}
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the returned result here
            val data = result.data
            // Do something with the data
            val newmWalletConnectionId =
                data?.getStringExtra(BarcodeScannerActivity.NEWM_WALLET_CONNECTION_ID).orEmpty()
            // create message
            val message =
                context.getString(R.string.wallet_link_connected_message, newmWalletConnectionId)
            // show message
            context.shortToast(message)
            onConnectWalletClick(newmWalletConnectionId)
        }
    }

    val requestPermission = rememberRequestPermissionIntent(
        onGranted = { /*TODO*/ },
        onDismiss = { /*TODO*/ })

    val label = if (isWalletConnected) {
        R.string.profile_connect_new_wallet_button_label
    } else {
        R.string.profile_connect_wallet_button_label
    }

    ProfileButton(
        label = stringResource(id = label),
        onClick = {
            context.run {
                doWithPermission(
                    onGranted = {
                        val intent = Intent(this, BarcodeScannerActivity::class.java)
                        launcher.launch(intent)
                    },
                    requestPermissionLauncher = requestPermission,
                    appPermission = AppPermission.CAMERA
                )
            }
        },
    )
}

@Composable
fun WalletButton(
    openWalletDialog: MutableState<Boolean>,
    isWalletConnected: Boolean,
    eventLogger: NewmAppEventLogger,
    disconnectWallet: () -> Unit,
    onConnectWalletClick: (String) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the returned result here
            val data = result.data
            // Do something with the data
            val newmWalletConnectionId =
                data?.getStringExtra(BarcodeScannerActivity.NEWM_WALLET_CONNECTION_ID).orEmpty()
            // create message
            val message =
                context.getString(R.string.wallet_link_connected_message, newmWalletConnectionId)
            // show message
            context.shortToast(message)
            onConnectWalletClick(newmWalletConnectionId)
        }
    }

    val onGranted = {
        val intent = Intent(context, BarcodeScannerActivity::class.java)
        launcher.launch(intent)
    }

    val requestPermission = rememberRequestPermissionIntent(
        onGranted = onGranted,
        onDismiss = { /*TODO*/ })


    if (isWalletConnected) {

        ProfileButton(
            label = stringResource(id = R.string.profile_disconnect_wallet_button_label),
            modifier = Modifier.drawWithBrush(disconnectWalletButtonTextGradient),
            onClick = { openWalletDialog.value = true },
            backgroundBrush = disconnectWalletButtonGradient,
            textStyle = disconnectButtonLabelStyle
        )
        ConfirmationDialog(
            title = stringResource(R.string.profile_unlink_dialog_title),
            message = stringResource(R.string.profile_unlink_dialog_message),
            eventLogger = eventLogger,
            isOpen = openWalletDialog,
            onConfirm = {
                disconnectWallet()
            },
            onDismiss = {
                // Handle the cancellation of logout here
                openWalletDialog.value = false
            }
        )
    } else {
        ProfileButton(
            label = stringResource(id = R.string.profile_connect_wallet_button_label),
            onClick = {
                context.doWithPermission(
                    onGranted = onGranted,
                    requestPermissionLauncher = requestPermission,
                    appPermission = AppPermission.CAMERA
                )
            },
        )
    }
}