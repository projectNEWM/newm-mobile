package io.newm.screens.account

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.permissions.AppPermission
import io.newm.core.ui.permissions.doWithPermission
import io.newm.core.ui.permissions.rememberRequestPermissionIntent
import io.newm.core.ui.text.formTextFieldStyle
import io.newm.core.ui.text.formTitleStyle
import io.newm.feature.barcode.scanner.BarcodeScannerActivity
import io.newm.screens.profile.ProfileBanner
import io.newm.shared.public.models.User
import org.koin.compose.koinInject

internal const val TAG_USER_ACCOUNT_VIEW_SCREEN = "TAG_USER_ACCOUNT_VIEW_SCREEN"

@Composable
fun UserAccountScreen(
    onEditProfileClick: () -> Unit,
    viewModel: UserAccountViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()
    when (state) {
        UserAccountState.Loading -> LoadingScreen()
        is UserAccountState.Content -> {
            UserAccountContent(
                user = (state as UserAccountState.Content).profile,
                isWalletConnected = (state as UserAccountState.Content).isWalletConnected,
                onConnectWalletClick = { xpubKey -> viewModel.connectWallet(xpubKey) },
                onEditProfileClick = onEditProfileClick,
                disconnectWallet = { viewModel.disconnectWallet() },
                logout = { viewModel.logout() }
            )
        }
    }
}

@Composable
fun UserAccountContent(
    user: User,
    isWalletConnected: Boolean,
    onConnectWalletClick: (String) -> Unit,
    onEditProfileClick: () -> Unit,
    disconnectWallet: () -> Unit,
    logout: () -> Unit,
) {
    val context = LocalContext.current
    // Setup the launcher with the contract and the callback
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle the returned result here
            val data = result.data
            // Do something with the data
            val xpubKey = data?.getStringExtra(BarcodeScannerActivity.XPUB_KEY).orEmpty()
            Toast.makeText(context, "Wallet connected $xpubKey", Toast.LENGTH_SHORT).show()
            onConnectWalletClick(xpubKey)
        }
    }

    val requestPermission = rememberRequestPermissionIntent(
        onGranted = { /*TODO*/ },
        onDismiss = { /*TODO*/ })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_USER_ACCOUNT_VIEW_SCREEN),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileBanner(
            bannerUrl = user.bannerUrl.orEmpty(),
            avatarUrl = user.pictureUrl.orEmpty()
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = user.firstName.orEmpty() + " " + user.lastName.orEmpty(),
            style = formTitleStyle
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = user.email.orEmpty(),
            style = formTextFieldStyle
        )
        SecondaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Edit Profile",
            onClick = { onEditProfileClick() }
        )

        SecondaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Logout",
            onClick = { logout() }
        )

        if (isWalletConnected) {
            SecondaryButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Disconnect Wallet",
                onClick = {
                    disconnectWallet()
                    Toast.makeText(context, "Wallet has been disconnected.", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        } else {
            PrimaryButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Connect Wallet",
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
                }
            )
        }
    }
}