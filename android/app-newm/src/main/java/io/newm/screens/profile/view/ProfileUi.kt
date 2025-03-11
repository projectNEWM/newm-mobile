package io.newm.screens.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.LoadingScreen
import io.newm.screens.profile.OnBottomSheetVisible
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnDisconnectWallet
import io.newm.screens.profile.OnEditProfile
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.screens.profile.OnVisitRecordStore
import io.newm.screens.profile.OnWalletDialogOpened
import io.newm.screens.profile.OnWalletsScreen
import io.newm.screens.profile.ProfileAppBar
import io.newm.screens.profile.ProfileBottomSheetLayout
import io.newm.screens.profile.ProfileHeader
import io.newm.shared.public.models.User
import kotlinx.coroutines.launch

internal const val TAG_USER_ACCOUNT_VIEW_SCREEN = "TAG_USER_ACCOUNT_VIEW_SCREEN"

@Composable
fun ProfileUi(
    state: ProfileUiState,
    modifier: Modifier = Modifier
) {
    when (state) {
        ProfileUiState.Loading -> LoadingScreen()
        is ProfileUiState.Content -> {
            ProfileUiContent(
                state = state,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ProfileUiContent(
    state: ProfileUiState.Content,
    modifier: Modifier
) {
    val onEvent = state.eventSink
    val openWalletDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    LaunchedEffect(openWalletDialog) {
        if(openWalletDialog.value) {
            onEvent(OnWalletDialogOpened)
        }
    }
    val user = state.profile
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ProfileBottomSheetLayout(
        modifier = modifier.fillMaxSize(),
        sheetState = sheetState,
        onLogout = { onEvent(OnLogout) },
        onShowTermsAndConditions = { onEvent(OnShowTermsAndConditions) },
        onShowPrivacyPolicy = { onEvent(OnShowPrivacyPolicy) },
        onBottomSheetVisible = { onEvent(OnBottomSheetVisible) },
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag(TAG_USER_ACCOUNT_VIEW_SCREEN),
            topBar = {
                Column {
                    ProfileAppBar(
                        bannerUrl = user.bannerUrl.orEmpty(),
                        avatarUrl = user.pictureUrl.orEmpty(),
                        onOverflowTapped = { scope.launch { sheetState.show() } },
                    )
                    ProfileHeader(
                        firstName = user.firstName.orEmpty(),
                        lastName = user.lastName.orEmpty(),
                        email = user.email.orEmpty(),
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                ProfileButton(
                    label = stringResource(id = R.string.profile_edit_button_label),
                    onClick = { onEvent(OnEditProfile) },
                )

                if (!state.showMultiWallets) {
                    WalletButton(
                        openWalletDialog = openWalletDialog,
                        isWalletConnected = state.isWalletConnected,
                        disconnectWallet = { onEvent(OnDisconnectWallet) }
                    ) { newmWalletConnectionId -> onEvent(OnConnectWallet(newmWalletConnectionId)) }
                }

                Spacer(modifier = Modifier.weight(1F))

                if (!state.showRecordStore) {
                    RecordStorePanel(
                        onClick = { onEvent(OnVisitRecordStore) },
                    )
                }

                if (state.showMultiWallets) {
                    if (state.isWalletConnected) {
                        ProfileButton(
                            label = stringResource(
                                id = R.string.profile_connected_wallets_button_label,
                                state.userConnectedWallets.size
                            ),
                            onClick = { onEvent(OnWalletsScreen) }
                        )
                    }
                    WalletsButton(
                        isWalletConnected = state.isWalletConnected,
                        onConnectWalletClick = { newmWalletConnectionId ->
                            onEvent(OnConnectWallet(newmWalletConnectionId))
                        }
                    )
                }
            }
        }
    }
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
                showRecordStore = false,
                showMultiWallets = false
            ),
            ProfileUiState.Content(
                profile = User(
                    id = "",
                    createdAt = "",
                ),
                isWalletConnected = true,
                eventSink = {},
                showRecordStore = true,
                showMultiWallets = true
            )
        )
}
