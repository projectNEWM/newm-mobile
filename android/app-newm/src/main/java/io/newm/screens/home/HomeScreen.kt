package io.newm.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.newm.core.resources.R

internal const val TAG_HOME_SCREEN = "TAG_HOME_SCREEN"

@Composable
fun HomeScreen(
    onThisWeekViewAll: () -> Unit,
    onRecentlyPlayedViewAll: () -> Unit,
    onMusicViewDetails: (MusicModel) -> Unit,
    onArtistViewDetails: (ArtistModel) -> Unit,
    onArtistListViewMore: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .testTag(TAG_HOME_SCREEN),
        verticalArrangement = Arrangement.Top
    ) {
        WelcomeBar(
            name = "Abel", //TODO: Replace Hardcoded values with values from ViewModel
            avatarUrl = "https://cdns-images.dzcdn.net/images/artist/033d460f704896c9caca89a1d753a137/200x200.jpg",
        )
        ThisWeekCarousel(
            followers = 12, //TODO: Replace Hardcoded values with values from ViewModel
            royalties = 51.56,
            earnings = 2.15,
            onViewAll = onThisWeekViewAll
        )
        MusicCarousel(
            title = stringResource(id = R.string.title_explore_music_carousel),
            musicModels = exploreMusicModels, //TODO: Replace Hardcoded values with values from ViewModel
            onViewDetails = onMusicViewDetails
        )
        MusicCarousel(
            title = stringResource(id = R.string.title_recently_played_music_carousel),
            musicModels = recentlyPlayedMusicModels,
            onViewDetails = onMusicViewDetails,
            onViewAll = onRecentlyPlayedViewAll
        )
        MusicCarousel(
            title = stringResource(id = R.string.title_just_released_music_carousel),
            musicModels = justReleasedMusicModels,
            onViewDetails = onMusicViewDetails
        )
        MusicCarousel(
            title = stringResource(id = R.string.title_just_for_you_music_carousel),
            musicModels = justForYouMusicModels,
            onViewDetails = onMusicViewDetails
        )
        ArtistList(
            title = stringResource(id = R.string.title_artist_list),
            artistModels = artistListModels,
            onViewDetails = onArtistViewDetails,
            onViewMore = onArtistListViewMore
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onThisWeekViewAll = {},
        onRecentlyPlayedViewAll = {},
        onMusicViewDetails = {},
        onArtistViewDetails = {},
        onArtistListViewMore = {}
    )
}