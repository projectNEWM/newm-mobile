package io.newm.screens.library.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import android.provider.Settings
import android.net.Uri
import io.newm.screens.library.TAG_NFT_LIBRARY_SCREEN

@Composable
fun LinkWalletScreen(onConnectWallet: (String) -> Unit) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the returned result here
            val data = result.data
            // Do something with the data
            val xpubKey = data?.getStringExtra(BarcodeScannerActivity.XPUB_KEY).orEmpty()
            Toast.makeText(context, "Wallet connected $xpubKey", Toast.LENGTH_SHORT).show()
            onConnectWallet(xpubKey)
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag(TAG_NFT_LIBRARY_SCREEN),
        contentAlignment = Alignment.BottomCenter // This centers the content both horizontally and vertically
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

