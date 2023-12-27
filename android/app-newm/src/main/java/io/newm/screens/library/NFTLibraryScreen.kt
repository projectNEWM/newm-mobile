package io.newm.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.core.theme.CerisePink
import io.newm.core.theme.Gray16
import io.newm.core.theme.GraySuit
import io.newm.core.theme.SteelPink
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.theme.raleway
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.text.SearchBar
import io.newm.core.ui.utils.ErrorScreen
import io.newm.core.ui.utils.drawWithBrush
import io.newm.core.ui.utils.secondsToMinutesSecondsString
import io.newm.core.ui.utils.textGradient
import io.newm.feature.musicplayer.MiniPlayer
import io.newm.screens.library.screens.EmptyWalletScreen
import io.newm.screens.library.screens.LinkWalletScreen
import io.newm.screens.library.screens.ZeroSearchResults
import io.newm.shared.public.models.NFTTrack
import org.koin.compose.koinInject

internal const val TAG_NFT_LIBRARY_SCREEN = "TAG_NFT_LIBRARY_SCREEN"

@Composable
fun NFTLibraryScreen(
    onPlaySong: (NFTTrack) -> Unit,
    goToProfile: () -> Unit,
    viewModel: NFTLibraryViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_NFT_LIBRARY_SCREEN)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_nft_library),
            modifier = Modifier.padding(vertical = 16.dp),
            style = TextStyle(
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                brush = textGradient(SteelPink, CerisePink)
            )
        )
        when (state) {
            NFTLibraryState.Loading -> LoadingScreen(modifier = Modifier.padding(horizontal = 16.dp))
            NFTLibraryState.LinkWallet -> LinkWalletScreen(goToProfile)
            NFTLibraryState.EmptyWallet -> EmptyWalletScreen()
            is NFTLibraryState.Error -> ErrorScreen((state as NFTLibraryState.Error).message)
            is NFTLibraryState.Content -> {
                val content = state as NFTLibraryState.Content
                NFTTracks(
                    tracks = content.nftTracks,
                    showZeroResultsFound = content.showZeroResultFound,
                    onQueryChange = viewModel::onQueryChange,
                    onPlaySong = onPlaySong
                )
            }


        }
    }

}


@Composable
fun NFTTracks(
    tracks: List<NFTTrack>,
    showZeroResultsFound: Boolean,
    onQueryChange: (String) -> Unit,
    onPlaySong: (NFTTrack) -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = rememberScrollState())
                .testTag(TAG_NFT_LIBRARY_SCREEN)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    placeholderResId = R.string.library_search,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onQueryChange = onQueryChange
                )
                IconButton(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp),
                    onClick = {/*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_library_filter),
                        contentDescription = "Filter",
                        modifier = Modifier
                            .drawWithBrush(LibraryBrush)

                    )
                }
            }
            when {
                showZeroResultsFound -> ZeroSearchResults()
                tracks.isNotEmpty() -> {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Gray16)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            tracks.forEach { song ->
                                RowSongItem(song = song, onClick = onPlaySong)
                            }
                        }
                    }
                }
            }
        }
        MiniPlayer()
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
        )
    }
}

@Composable
private fun RowSongItem(
    song: NFTTrack,
    onClick: (NFTTrack) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = { onClick(song) })
    ) {
        AsyncImage(
            model = song.imageUrl,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Text(
                text = song.title,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = White
            )
            Text(
                text = song.artists.joinToString(","),
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = GraySuit
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = song.duration.secondsToMinutesSecondsString(),
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = GraySuit
        )
    }
}



