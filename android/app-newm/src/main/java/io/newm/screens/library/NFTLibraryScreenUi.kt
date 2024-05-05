@file:OptIn(FlowPreview::class, ExperimentalMaterialApi::class)

package io.newm.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.slack.circuit.foundation.internal.BackHandler
import io.newm.core.resources.R
import io.newm.core.theme.CerisePink
import io.newm.core.theme.Gray16
import io.newm.core.theme.GraySuit
import io.newm.core.theme.StatusGreen
import io.newm.core.theme.SteelPink
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.theme.raleway
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.text.SearchBar
import io.newm.core.ui.utils.ErrorScreen
import io.newm.core.ui.utils.drawWithBrush
import io.newm.core.ui.utils.textGradient
import io.newm.feature.musicplayer.MiniPlayer
import io.newm.feature.musicplayer.MusicPlayerScreen
import io.newm.feature.musicplayer.rememberMediaPlayer
import io.newm.screens.library.screens.EmptyWalletScreen
import io.newm.screens.library.screens.LinkWalletScreen
import io.newm.screens.library.screens.ZeroSearchResults
import io.newm.shared.public.models.NFTTrack
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.roundToInt

internal const val TAG_NFT_LIBRARY_SCREEN = "TAG_NFT_LIBRARY_SCREEN"


@Composable
fun NFTLibraryScreen(
) {
    val mediaPlayer = rememberMediaPlayer()

    mediaPlayer ?: return

    val viewModel: NFTLibraryViewModel = getViewModel {
        parametersOf(mediaPlayer)
    }

    val state by viewModel.state.collectAsState()

    NFTLibraryScreenUi(
        state = state,
        onConnectWallet = { newmWalletConnectionId ->
            viewModel.connectWallet(newmWalletConnectionId)
        },
        onQueryChange = { newQuery ->
            viewModel.onQueryChange(newQuery)
        },
        onDownloadSong = { trackId ->
            viewModel.onDownloadSong(trackId)
        },
        onPlaySong = { track ->
            viewModel.playSong(track)
        }
    )

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NFTLibraryScreenUi(
    state: NFTLibraryState,
    onConnectWallet: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onDownloadSong: (String) -> Unit,
    onPlaySong: (NFTTrack) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    BackHandler(
        enabled = sheetState.isVisible
    ) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            MusicPlayerScreen(
                onNavigateUp = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                })
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag(TAG_NFT_LIBRARY_SCREEN)
                .systemBarsPadding()
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
                NFTLibraryState.LinkWallet -> LinkWalletScreen { xpubKey ->
                    onConnectWallet(xpubKey)
                }

                NFTLibraryState.EmptyWallet -> EmptyWalletScreen()
                is NFTLibraryState.Error -> ErrorScreen((state as NFTLibraryState.Error).message)
                is NFTLibraryState.Content -> {
                    val content = state as NFTLibraryState.Content
                    NFTTracks(
                        nftTracks = content.nftTracks,
                        streamTokenTracks = content.streamTokenTracks,
                        showZeroResultsFound = content.showZeroResultFound,
                        onQueryChange = onQueryChange,
                        onPlaySong = onPlaySong,
                        onDownloadSong = onDownloadSong,
                        onPlayerClicked = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun NFTTracks(
    nftTracks: List<NFTTrack>,
    streamTokenTracks: List<NFTTrack>,
    showZeroResultsFound: Boolean,
    onQueryChange: (String) -> Unit,
    onPlaySong: (NFTTrack) -> Unit,
    onDownloadSong: (String) -> Unit,
    onPlayerClicked: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag(TAG_NFT_LIBRARY_SCREEN)
        ) {
            item {
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
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp),
                        onClick = { scope.launch { sheetState.show() } }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_library_filter),
                            contentDescription = "Filter",
                            modifier = Modifier
                                .clickable { scope.launch { sheetState.show() } }
                                .drawWithBrush(LibraryBrush)
                        )
                    }
                }
            }
            when {
                showZeroResultsFound -> item { ZeroSearchResults() }

                nftTracks.isNotEmpty() || streamTokenTracks.isNotEmpty() -> {
                    items(nftTracks + streamTokenTracks, key = { track ->
                        // Use the unique ID as the key
                        track.id
                    }) { track ->
                        Box(
                            modifier = Modifier
                                .background(Gray16)
                        ) {
                            TrackRowItemWrapper(
                                track = track,
                                onPlaySong = onPlaySong,
                                onDownloadSong = { onDownloadSong(track.id) }
                            )
                        }
                    }
                }
            }
            // Add a spacer as the last item, with the same height as a track item.
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            MiniPlayer(
                modifier = Modifier
                    .clickable { onPlayerClicked() } // replace with current song or drop param altogether
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TrackRowItemWrapper(
    track: NFTTrack,
    onPlaySong: (NFTTrack) -> Unit,
    onDownloadSong: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = false)
    val deltaX = with(LocalDensity.current) { 82.dp.toPx() }
    Box(
        Modifier
            .height(64.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Horizontal,
                enabled = !track.isDownloaded,
                reverseDirection = true,
                anchors = mapOf(
                    0f to false,
                    deltaX to true,
                ),
            )
    ) {
        if (!track.isDownloaded) {
            RevealedPanel(onDownloadSong)
        }
        TrackRowItem(
            track = track,
            onClick = onPlaySong,
            modifier = Modifier.offset {
                IntOffset(
                    x = -swipeableState.offset.value.roundToInt(),
                    y = 0
                )
            }
        )
    }
}

@Composable
private fun TrackRowItem(
    track: NFTTrack,
    onClick: (NFTTrack) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .background(color = Gray16)
            .clickable(onClick = { onClick(track) })
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        AsyncImage(
            model = track.imageUrl,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Text(
                text = track.title,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = White
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (track.isDownloaded) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_downloaded),
                        contentDescription = "Downloaded",
                        tint = StatusGreen
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                }
                Text(
                    text = track.artists.joinToString(","),
                    fontFamily = inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = GraySuit
                )
            }
        }
    }
}
