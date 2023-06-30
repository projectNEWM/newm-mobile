package io.newm.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.R
import io.newm.core.theme.Black
import io.newm.core.ui.buttons.PrimaryButton
import kotlinx.coroutines.launch

internal const val TAG_PROFILE_SCREEN = "TAG_PROFILE_SCREEN"

//TODO This is a temporary model for the profile needs to be replaced with real model from ViewModel
data class ProfileModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val bannerUrl: String,
    val avatarUrl: String,
    val currentPassword: String? = null,
    val newPassword: String? = null,
    val confirmPassword: String? = null
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    isBottomBarVisible: MutableState<Boolean>,
    onNavigateUp: () -> Unit,
    onLogout: () -> Unit,
    onShowTermsAndConditions: () -> Unit,
    onShowPrivacyPolicy: () -> Unit,
    onShowDocuments: () -> Unit,
    onShowAskTheCommunity: () -> Unit,
    onShowFaq: () -> Unit
) {
    //TODO This should come from the ViewModel
    val profile = ProfileModel(
        firstName = "Abel",
        lastName = "Tesfaye",
        email = "abel@gmail.com",
        bannerUrl = "https://images.pexels.com/photos/3002/black-and-white-surfer-surfing.jpg",
        avatarUrl = "https://cdns-images.dzcdn.net/images/artist/033d460f704896c9caca89a1d753a137/200x200.jpg"
    )
    var updatedProfile by remember { mutableStateOf(profile) }
    val isModified = profile != updatedProfile
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    isBottomBarVisible.value = !sheetState.isVisible

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
                bannerUrl = profile.bannerUrl,
                avatarUrl = profile.avatarUrl
            )
            Spacer(modifier = Modifier.height(40.dp))
            ProfileForm(
                profile = updatedProfile,
                onProfileUpdated = { updatedProfile = it }
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
