package io.newm.screens.wallets.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import io.newm.core.resources.R
import io.newm.core.ui.permissions.AppPermission
import io.newm.core.ui.permissions.doWithPermission
import io.newm.core.ui.permissions.rememberRequestPermissionIntent
import io.newm.core.ui.utils.shortToast
import io.newm.feature.barcode.scanner.BarcodeScannerActivity

internal fun ActivityResult.onActivityResultOk(
    context: Context,
    onResult: (String) -> Unit,
) {
    // Do something with the data
    val walletId = data?.getStringExtra(BarcodeScannerActivity.NEWM_WALLET_CONNECTION_ID).orEmpty()
    // create message
    val message = context.getString(R.string.wallet_link_connected_message, walletId)
    // show message
    context.shortToast(message)
    onResult(walletId)
}

@Composable
fun rememberBarcodeScannerLauncher(
    onDismiss: () -> Unit = { /*TODO We need a flow for when the user denies permissions*/ },
    onResult: (String) -> Unit,
): () -> Unit {
    val context = LocalContext.current
    val intent = remember { Intent(context, BarcodeScannerActivity::class.java) }
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.onActivityResultOk(context, onResult)
            }
        }

    val requestPermission =
        rememberRequestPermissionIntent(
            onGranted = { launcher.launch(intent) },
            onDismiss = onDismiss,
        )

    return remember {
        {
            context.doWithPermission(
                onGranted = { launcher.launch(intent) },
                requestPermissionLauncher = requestPermission,
                appPermission = AppPermission.CAMERA,
            )
        }
    }
}
