package io.newm.screens.profile.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.Black
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.text.formEmailStyle
import io.newm.core.ui.text.formNameStyle
import io.newm.screens.profile.ProfileBanner
import io.newm.screens.profile.ProfileBottomSheet
import io.newm.screens.profile.ProfileForm
import io.newm.shared.public.models.User
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

internal const val TAG_PROFILE_SCREEN = "TAG_PROFILE_SCREEN"

@Composable
fun ProfileRoute(
    onNavigateUp: () -> Unit,
    viewModel: ProfileEditViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        ProfileState.Loading -> LoadingScreen()
        is ProfileState.Content -> {
            ProfileScreen(
                onNavigateUp = onNavigateUp,
                user = (state as ProfileState.Content).profile,
                onLogout = {},
                onShowTermsAndConditions = {}, //TODO: Link the appropriate page
                onShowPrivacyPolicy = {}, //TODO: Link the appropriate page
                onShowDocuments = {}, //TODO: Link the appropriate page
                onShowAskTheCommunity = {}, //TODO: Link the appropriate page
                onShowFaq = {}, //TODO: Link the appropriate page
                onConnectWallet = {} //TODO: Link to appropriate page
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    onNavigateUp: () -> Unit,
    user: User,
    onLogout: () -> Unit,
    onShowTermsAndConditions: () -> Unit,
    onShowPrivacyPolicy: () -> Unit,
    onShowDocuments: () -> Unit,
    onShowAskTheCommunity: () -> Unit,
    onShowFaq: () -> Unit,
    onConnectWallet: () -> Unit
) {
    var updatedProfile by remember { mutableStateOf(user) }
    val isModified = user != updatedProfile
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Black,
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { scope.launch { sheetState.show() } }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Overflow")
                    }
                }
            )
        },
        floatingActionButton = {
            if (isModified) {
                PrimaryButton(
                    text = stringResource(id = R.string.profile_save_button_label),
                    modifier = Modifier.padding(horizontal = 20.dp),
                    onClick = {} //TODO Save updatedProfile into ViewModel
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .testTag(TAG_PROFILE_SCREEN),
            verticalArrangement = Arrangement.Top
        ) {
            ProfileBanner(
                bannerUrl = user.bannerUrl.orEmpty(),
                avatarUrl = user.pictureUrl.orEmpty()
            )
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = user.firstName.orEmpty() + " " + user.lastName.orEmpty(),
                style = formNameStyle
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = user.email.orEmpty(),
                style = formEmailStyle
            )
            Spacer(modifier = Modifier.height(40.dp))
            ProfileForm(
                profile = updatedProfile,
                onProfileUpdated = { updatedProfile = it },
                onConnectWallet = onConnectWallet
            )
        }
    }

    ProfileBottomSheet(
        sheetState = sheetState,
        onLogout = onLogout,
        onShowTermsAndConditions = onShowTermsAndConditions,
        onShowPrivacyPolicy = onShowPrivacyPolicy,
        onShowDocuments = onShowDocuments,
        onShowAskTheCommunity = onShowAskTheCommunity,
        onShowFaq = onShowFaq
    )
}
