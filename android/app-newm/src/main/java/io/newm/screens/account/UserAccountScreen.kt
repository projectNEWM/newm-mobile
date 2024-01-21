package io.newm.screens.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.Gray100
import io.newm.core.theme.Gray500
import io.newm.core.theme.SystemRed
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.ui.ConfirmationDialog
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
private const val PRIVACY_POLICY = "https://newm.io/privacy-policy/"
private const val COMMUNITY_GUIDELINES = "https://newm.io/guidelines/"

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

    val scrollState = rememberScrollState()
    val openDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .testTag(TAG_USER_ACCOUNT_VIEW_SCREEN),
        horizontalAlignment = Alignment.Start
    ) {
        ProfileBanner(
            bannerUrl = user.bannerUrl.orEmpty(),
            avatarUrl = user.pictureUrl.orEmpty()
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = user.firstName.orEmpty() + " " + user.lastName.orEmpty(),
            style = formTitleStyle
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = user.email.orEmpty(),
            style = formTextFieldStyle
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(id = R.string.user_account_settings),
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Gray100
        )
        SecondaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Edit Profile",
            onClick = { onEditProfileClick() },
            borderColor = Gray500
        )

        if (isWalletConnected) {
            SecondaryButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Disconnect Wallet",
                borderColor = Gray500,
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(id = R.string.user_account_legal),
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Gray100
        )
        SecondaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.profile_terms_and_condition),
            textColor = White,
            borderColor = Gray500,
            onClick = {}
        )

        SecondaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.profile_privacy_policy),
            textColor = White,
            borderColor = Gray500,
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY)))
            }
        )

        SecondaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.user_account_guidelines),
            textColor = White,
            borderColor = Gray500,
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(COMMUNITY_GUIDELINES)))
            }
        )
        LogoutButtonWithConfirmation(name = user.firstName ?: user.nickname ?: stringResource(id = R.string.buddy) , openDialog = openDialog, logout = logout)
    }

}

@Composable
fun LogoutButtonWithConfirmation(
    name: String ,
    openDialog: MutableState<Boolean>,
    logout: () -> Unit,
) {
    SecondaryButton(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        text = stringResource(R.string.user_account_logout),
        textColor = SystemRed,
        borderColor = SystemRed,
        onClick = { openDialog.value = true }
    )
    ConfirmationDialog(
        title = stringResource(R.string.logout_dialog_title, name),
        message = stringResource(R.string.logout_dialog_message),
        isOpen = openDialog,
        onConfirm = {
            logout()
        },
        onDismiss = {
            // Handle the cancellation of logout here
            openDialog.value = false
        }
    )
}
