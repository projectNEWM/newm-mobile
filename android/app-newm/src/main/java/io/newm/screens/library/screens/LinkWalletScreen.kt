package io.newm.screens.library.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.newm.core.ui.permissions.AppPermission
import io.newm.core.ui.permissions.doWithPermission
import io.newm.core.ui.permissions.rememberRequestPermissionIntent
import io.newm.core.ui.wallet.ConnectWalletPanel
import io.newm.feature.barcode.scanner.BarcodeScannerActivity
import io.newm.screens.library.TAG_NFT_LIBRARY_SCREEN

@Composable
fun LinkWalletScreen(
    modifier: Modifier = Modifier,
    onConnectWallet: (String) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the returned result here
            val data = result.data
            // Do something with the data
            val newmWalletConnectionId = data?.getStringExtra(BarcodeScannerActivity.NEWM_WALLET_CONNECTION_ID).orEmpty()
            Toast.makeText(context, "Wallet connected $newmWalletConnectionId", Toast.LENGTH_SHORT).show()
            onConnectWallet(newmWalletConnectionId)
        }
    }

    val onRequestPermissionGranted = {
        val intent = Intent(context, BarcodeScannerActivity::class.java)
        launcher.launch(intent)
    }

    val navigateToAppSettings = {
        Toast.makeText(
            context,
            "NEWM Needs access to your camara in order to connect a wallet",
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }

    val requestPermission =
        rememberRequestPermissionIntent(onGranted = { onRequestPermissionGranted.invoke() },
            onDismiss = { navigateToAppSettings.invoke() })
    Box(
        modifier = modifier
            .padding(vertical = 16.dp)
            .defaultMinSize(minHeight = 200.dp)
            .testTag(TAG_NFT_LIBRARY_SCREEN),
        contentAlignment = Alignment.BottomCenter
    ) {
        ConnectWalletPanel(onButtonClick = {
            context.run {
                doWithPermission(
                    onGranted = { onRequestPermissionGranted.invoke() },
                    requestPermissionLauncher = requestPermission,
                    appPermission = AppPermission.CAMERA
                )
            }
        })
    }
}

