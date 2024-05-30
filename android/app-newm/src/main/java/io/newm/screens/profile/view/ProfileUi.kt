package io.newm.screens.profile.view

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.theme.SystemRed
import io.newm.core.ui.ConfirmationDialog
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.permissions.AppPermission
import io.newm.core.ui.permissions.doWithPermission
import io.newm.core.ui.permissions.rememberRequestPermissionIntent
import io.newm.feature.barcode.scanner.BarcodeScannerActivity
import io.newm.screens.library.screens.RecordStoreCta
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnDisconnectWallet
import io.newm.screens.profile.OnEditProfile
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.screens.profile.ProfileAppBar
import io.newm.screens.profile.ProfileBottomSheetLayout
import io.newm.screens.profile.ProfileHeader
import io.newm.shared.public.models.User
import kotlinx.coroutines.launch

internal const val TAG_USER_ACCOUNT_VIEW_SCREEN = "TAG_USER_ACCOUNT_VIEW_SCREEN"

@Composable
fun ProfileUi(
    state: ProfileUiState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        ProfileUiState.Loading -> LoadingScreen()
        is ProfileUiState.Content -> {
            ProfileUiContent(
                state = state,
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileUiContent(
    state: ProfileUiState.Content,
    modifier: Modifier,
) {
    val onEvent = state.eventSink
    val openWalletDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val user = state.profile
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val scope = rememberCoroutineScope()

    ProfileBottomSheetLayout(
        modifier = modifier.fillMaxSize(),
        sheetState = sheetState,
        onLogout = { onEvent(OnLogout) },
        onShowTermsAndConditions = { onEvent(OnShowTermsAndConditions) },
        onShowPrivacyPolicy = { onEvent(OnShowPrivacyPolicy) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .testTag(TAG_USER_ACCOUNT_VIEW_SCREEN),
        ) {
            ProfileAppBar(
                bannerUrl = user.bannerUrl.orEmpty(),
                avatarUrl = user.pictureUrl.orEmpty(),
                onOverflowTapped = { scope.launch { sheetState.show() } },
            )
            ProfileHeader(
                nickname = user.nickname.orEmpty(),
                email = user.email.orEmpty(),
            )
            SecondaryButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Edit Profile",
                onClick = { onEvent(OnEditProfile) }
            )

            WalletButton(
                openWalletDialog = openWalletDialog,
                isWalletConnected = state.isWalletConnected,
                disconnectWallet = { onEvent(OnDisconnectWallet) }
            ) { xpubKey -> onEvent(OnConnectWallet(xpubKey)) }

            Spacer(Modifier.weight(1f))

            RecordStoreCta()
        }
    }

}

@Composable
private fun WalletButton(
    openWalletDialog: MutableState<Boolean>,
    isWalletConnected: Boolean,
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
            val xpubKey = data?.getStringExtra(BarcodeScannerActivity.XPUB_KEY).orEmpty()
            Toast.makeText(context, "Wallet connected $xpubKey", Toast.LENGTH_SHORT).show()
            onConnectWalletClick(xpubKey)
        }
    }

    val requestPermission = rememberRequestPermissionIntent(
        onGranted = { /*TODO*/ },
        onDismiss = { /*TODO*/ })


    if (isWalletConnected) {
        SecondaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Disconnect Wallet",
            onClick = {
                openWalletDialog.value = true
            }
        )
        ConfirmationDialog(
            title = "Unlink Cardano Wallet",
            message = "Are you sure you want to disconnect your wallet?",
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
        PrimaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Connect Cardano Wallet",
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

@Composable
private fun LogoutButtonWithConfirmation(
    name: String,
    openDialog: MutableState<Boolean>,
    logout: () -> Unit,
) {
    SecondaryButton(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        text = stringResource(R.string.user_account_logout),
        textColor = SystemRed,
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

@Preview(showBackground = true)
@Composable
internal fun UserAccountScreenPreview(
    @PreviewParameter(AccountScreenPreviewProvider::class)
    state: ProfileUiState,
) {
    NewmTheme(darkTheme = true) {
        ProfileUi(
            state = state,
            modifier = Modifier
        )
    }
}

internal class AccountScreenPreviewProvider : PreviewParameterProvider<ProfileUiState> {
    override val values: Sequence<ProfileUiState>
        get() = sequenceOf(
            ProfileUiState.Loading,
            ProfileUiState.Content(
                profile = User(
                    id = "",
                    createdAt = "",
                    firstName = "John",
                    lastName = "Doe",
                    email = "john@doe.com",
                    biography = "I love music."
                ),
                isWalletConnected = false,
                eventSink = {},
            ),
            ProfileUiState.Content(
                profile = User(
                    id = "",
                    createdAt = "",
                ),
                isWalletConnected = true,
                eventSink = {},
            )
        )
}
