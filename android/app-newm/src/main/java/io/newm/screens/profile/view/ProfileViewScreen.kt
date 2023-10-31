package io.newm.screens.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.text.formTextFieldStyle
import io.newm.core.ui.text.formTitleStyle
import io.newm.screens.profile.ProfileBanner
import io.newm.shared.models.User
import org.koin.compose.koinInject

internal const val TAG_PROFILE_VIEW_SCREEN = "TAG_PROFILE_VIEW_SCREEN"

@Composable
fun ProfileViewScreen(
    onConnectWalletClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    viewModel: ProfileReadOnlyViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()
    when (state) {
        ProfileViewState.Loading -> LoadingScreen()
        is ProfileViewState.Content -> {
            ProfileViewContent(
                user = (state as ProfileViewState.Content).profile,
                onConnectWalletClick = onConnectWalletClick,
                onEditProfileClick = onEditProfileClick,
                logout = { viewModel.logout() }
            )
        }
    }
}

@Composable
fun ProfileViewContent(
    user: User,
    onConnectWalletClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    logout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_PROFILE_VIEW_SCREEN),
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

        PrimaryButton(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "Connect Wallet",
            onClick = { onConnectWalletClick() }
        )

    }
}