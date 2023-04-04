package io.newm.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

internal const val TAG_HOME_SCREEN = "TAG_HOME_SCREEN"

@Composable
fun HomeScreen(
    onShowProfile: () -> Unit,
    onThisWeekViewAll: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_HOME_SCREEN),
        verticalArrangement = Arrangement.Top
    ) {
        WelcomeBar(
            name = "Abel", //TODO: Replace Hardcoded values with values from ViewModel
            avatarUrl = "https://cdns-images.dzcdn.net/images/artist/033d460f704896c9caca89a1d753a137/200x200.jpg",
            onAvatarClick = onShowProfile
        )
        ThisWeekCarousel(
            followers = 12, //TODO: Replace Hardcoded values with values from ViewModel
            royalties = 51.56,
            earnings = 2.15,
            onViewAll = onThisWeekViewAll
        )
    }
}
