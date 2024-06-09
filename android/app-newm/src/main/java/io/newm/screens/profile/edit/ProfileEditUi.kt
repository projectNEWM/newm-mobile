package io.newm.screens.profile.edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.NewmTheme
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.feature.login.screen.TextFieldState
import io.newm.screens.library.screens.LinkWalletScreen
import io.newm.screens.profile.OnBack
import io.newm.screens.profile.OnConnectWallet
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnSaveProfile
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.screens.profile.ProfileAppBar
import io.newm.screens.profile.ProfileBottomSheetLayout
import io.newm.screens.profile.ProfileForm
import io.newm.screens.profile.ProfileHeader
import io.newm.screens.profile.edit.ProfileEditUiState.Content
import io.newm.screens.profile.edit.ProfileEditUiState.Loading
import io.newm.shared.public.models.User
import io.newm.shared.public.models.mocks.mockUsers
import kotlinx.coroutines.launch

internal const val TAG_PROFILE_SCREEN = "TAG_PROFILE_SCREEN"

@Composable
fun ProfileEditUi(
    modifier: Modifier,
    state: ProfileEditUiState,
) {
    when (state) {
        Loading -> LoadingScreen()
        is Content -> {
            ProfileEditUiContent(
                modifier = modifier,
                state = state,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileEditUiContent(
    modifier: Modifier = Modifier,
    state: Content,
) {
    val onEvent = state.eventSink
    val profile = state.profile

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ProfileBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        onLogout = { onEvent(OnLogout) },
        onShowTermsAndConditions = { onEvent(OnShowTermsAndConditions) },
        onShowPrivacyPolicy = { onEvent(OnShowPrivacyPolicy) }
    ) {
        Column(
            modifier = Modifier
                .imePadding()
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .testTag(TAG_PROFILE_SCREEN),
            verticalArrangement = Arrangement.Top
        ) {
            ProfileAppBar(
                bannerUrl = profile.bannerUrl,
                avatarUrl = profile.pictureUrl,
                onOverflowTapped = { scope.launch { sheetState.show() } },
                onNavigationClick = { onEvent(OnBack) }
            )
            ProfileHeader(
                nickname = profile.nickname,
                email = profile.email,
            )
            Spacer(modifier = Modifier.height(40.dp))
            if (state.showConnectWallet) {
                LinkWalletScreen(
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    onEvent(OnConnectWallet(it))
                }
            }
            ProfileForm(
                email = profile.email,
                nicknameState = state.nicknameState,
                currentPasswordState = state.currentPasswordState,
                newPasswordState = state.newPasswordState,
                confirmNewPasswordState = state.confirmPasswordState,
            )
            Spacer(modifier = Modifier.height(40.dp))
            AnimatedVisibility(state.errorMessage != null) {
                Text(
                    text = state.errorMessage.orEmpty(),
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            PrimaryButton(
                enabled = state.submitButtonEnabled,
                text = stringResource(id = R.string.profile_save_button_label),
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onEvent(OnSaveProfile) }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
fun ScrimCircle(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                Color.Black.copy(alpha = 0.25f),
                shape = CircleShape
            )
    ) {
        content()
    }
}


@Preview
@Composable
private fun ProfileScreenPreview() {
    NewmTheme(
        darkTheme = true
    ) {
        ProfileEditUiContent(
            state = Content(
                profile = mockUsers.first().toProfile(),
                submitButtonEnabled = true,
                showConnectWallet = true,
                nicknameState = TextFieldState(),
                currentPasswordState = TextFieldState(),
                newPasswordState = TextFieldState(),
                confirmPasswordState = TextFieldState(),
                errorMessage = null,
                eventSink = {},
            )
        )
    }
}

private fun User.toProfile(): Content.Profile {
    return Content.Profile(
        email = email.orEmpty(),
        nickname = nickname.orEmpty(),
        pictureUrl = pictureUrl.orEmpty(),
        bannerUrl = bannerUrl.orEmpty(),
    )
}
