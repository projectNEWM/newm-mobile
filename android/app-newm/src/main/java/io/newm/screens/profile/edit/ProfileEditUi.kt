package io.newm.screens.profile.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import io.newm.screens.profile.OnBack
import io.newm.screens.profile.OnLogout
import io.newm.screens.profile.OnSaveProfile
import io.newm.screens.profile.OnShowAskTheCommunity
import io.newm.screens.profile.OnShowDocuments
import io.newm.screens.profile.OnShowFaq
import io.newm.screens.profile.OnShowPrivacyPolicy
import io.newm.screens.profile.OnShowTermsAndConditions
import io.newm.screens.profile.ProfileAppBar
import io.newm.screens.profile.ProfileBottomSheetLayout
import io.newm.screens.profile.ProfileEditUiEvent
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
                onEvent = state.eventSink,
                user = state.profile
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileEditUiContent(
    modifier: Modifier = Modifier,
    onEvent: (ProfileEditUiEvent) -> Unit,
    user: User,
) {
    var updatedProfile by remember { mutableStateOf(user) }
    val isModified = user != updatedProfile
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ProfileBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        onLogout = { onEvent(OnLogout) },
        onShowTermsAndConditions = { onEvent(OnShowTermsAndConditions) },
        onShowPrivacyPolicy = { onEvent(OnShowPrivacyPolicy) },
        onShowDocuments = { onEvent(OnShowDocuments) },
        onShowAskTheCommunity = { onEvent(OnShowAskTheCommunity) },
        onShowFaq = { onEvent(OnShowFaq) }
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
                bannerUrl = user.bannerUrl.orEmpty(),
                avatarUrl = user.pictureUrl.orEmpty(),
                onOverflowTapped = { scope.launch { sheetState.show() } },
                onNavigationClick = { onEvent(OnBack) }
            )
            ProfileHeader(
                nickname = user.nickname.orEmpty(),
                email = user.email.orEmpty(),
            )
            Spacer(modifier = Modifier.height(40.dp))
            ProfileForm(
                profile = updatedProfile,
                onProfileUpdated = { updatedProfile = it },
                onConnectWallet = {
                    // TODO
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            PrimaryButton(
                enabled = isModified,
                text = stringResource(id = R.string.profile_save_button_label),
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onEvent(OnSaveProfile(updatedProfile)) }
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
            onEvent = {},
            user = mockUsers.first(),
        )
    }
}
