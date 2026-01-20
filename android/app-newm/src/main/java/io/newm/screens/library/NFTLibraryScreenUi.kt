@file:OptIn(ExperimentalMaterialApi::class)

package io.newm.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import io.newm.core.resources.R
import io.newm.core.theme.CerisePink
import io.newm.core.theme.DarkPink
import io.newm.core.theme.DarkViolet
import io.newm.core.theme.Gray16
import io.newm.core.theme.GraySuit
import io.newm.core.theme.NewmTheme
import io.newm.core.theme.Purple
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
import io.newm.feature.musicplayer.service.DownloadState
import io.newm.screens.library.NFTLibraryEvent.OnApplyFilters
import io.newm.screens.library.NFTLibraryEvent.OnDownloadTrack
import io.newm.screens.library.NFTLibraryEvent.OnQueryChange
import io.newm.screens.library.NFTLibraryEvent.PlaySong
import io.newm.screens.library.screens.EmptyWalletScreen
import io.newm.screens.library.screens.LinkWalletScreen
import io.newm.screens.library.screens.ZeroSearchResults
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import io.newm.shared.commonPublic.analytics.events.AppScreens
import io.newm.shared.commonPublic.models.NFTTrack
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

internal const val TAG_NFT_LIBRARY_SCREEN = "TAG_NFT_LIBRARY_SCREEN"
internal val LibraryBrush = Brush.horizontalGradient(listOf(DarkViolet, DarkPink))

@Composable
fun NFTLibraryScreenUi(
    modifier: Modifier = Modifier,
    state: NFTLibraryState,
    eventLogger: NewmAppEventLogger,
) {
    Column(modifier = modifier.fillMaxSize().statusBarsPadding().testTag(TAG_NFT_LIBRARY_SCREEN)) {
        when (state) {
            NFTLibraryState.Loading -> {
                LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.LoadingScreen.name) }
                LoadingScreen()
            }

            is NFTLibraryState.LinkWallet -> {
                LaunchedEffect(Unit) {
                    eventLogger.logPageLoad(AppScreens.NFTLibraryLinkWalletScreen.name)
                }
                LinkWalletScreen(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) { newmWalletConnectionId ->
                    val eventSink = state.onConnectWallet
                    eventSink(newmWalletConnectionId)
                }
            }

            NFTLibraryState.EmptyWallet -> {
                LaunchedEffect(Unit) {
                    eventLogger.logPageLoad(AppScreens.NFTLibraryEmptyWalletScreen.name)
                }
                EmptyWalletScreen(eventLogger)
            }

            is NFTLibraryState.Error -> {
                LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.ErrorScreen.name) }
                ErrorScreen(
                    title = stringResource(R.string.nft_library_error_message),
                    message = state.message,
                )
            }

            is NFTLibraryState.Content -> {
                val eventSink = state.eventSink

                LaunchedEffect(Unit) { eventLogger.logPageLoad(AppScreens.NFTLibraryScreen.name) }
                Text(
                    text = stringResource(id = R.string.title_nft_library),
                    modifier = Modifier.padding(16.dp),
                    style =
                        TextStyle(
                            fontFamily = raleway,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            brush = textGradient(SteelPink, CerisePink),
                        ),
                )

                NFTTracks(
                    modifier = Modifier.weight(1f),
                    nftTracks = state.nftTracks,
                    streamTokenTracks = state.streamTokenTracks,
                    showZeroResultsFound = state.showZeroResultFound,
                    filters = state.filters,
                    onQueryChange = { query -> eventSink(OnQueryChange(query)) },
                    onPlaySong = { track -> eventSink(PlaySong(track)) },
                    onDownloadSong = { track -> eventSink(OnDownloadTrack(track)) },
                    refresh = { eventSink(NFTLibraryEvent.OnRefresh) },
                    refreshing = state.refreshing,
                    eventLogger = eventLogger,
                    onApplyFilters = { filters -> eventSink(OnApplyFilters(filters)) },
                    currentTrackId = state.currentTrackId,
                    downloadsEnabled = state.downloadsEnabled,
                    downloadStates = state.downloadStates,
                    onRemoveSong = { track -> eventSink(NFTLibraryEvent.OnRemoveDownload(track)) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NFTTracks(
    modifier: Modifier = Modifier,
    nftTracks: List<NFTTrack>,
    streamTokenTracks: List<NFTTrack>,
    showZeroResultsFound: Boolean,
    filters: NFTLibraryFilters,
    onQueryChange: (String) -> Unit,
    onPlaySong: (NFTTrack) -> Unit,
    onDownloadSong: (NFTTrack) -> Unit,
    onRemoveSong: (NFTTrack) -> Unit,
    refresh: () -> Unit,
    refreshing: Boolean,
    eventLogger: NewmAppEventLogger,
    onApplyFilters: (NFTLibraryFilters) -> Unit,
    currentTrackId: String?,
    downloadsEnabled: Boolean,
    downloadStates: Map<String, DownloadState>,
) {
    val scope = rememberCoroutineScope()
    val filterSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val pullRefreshState = rememberPullRefreshState(refreshing, refresh)

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier =
                Modifier.padding(horizontal = 16.dp).fillMaxSize().testTag(TAG_NFT_LIBRARY_SCREEN),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SearchBar(
                        placeholderResId = R.string.library_search,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        onQueryChange = onQueryChange,
                    )
                    IconButton(
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 16.dp),
                        onClick = { scope.launch { filterSheetState.show() } },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_library_filter),
                            contentDescription = stringResource(R.string.filter_description),
                            modifier = Modifier.drawWithBrush(LibraryBrush),
                        )
                    }
                }
            }
            when {
                showZeroResultsFound -> {
                    item { ZeroSearchResults() }
                }

                nftTracks.isNotEmpty() || streamTokenTracks.isNotEmpty() -> {
                    items(nftTracks + streamTokenTracks, key = { track -> track.id }) { track ->
                        Box(modifier = Modifier.background(Gray16)) {
                            TrackRowItemWrapper(
                                track = track,
                                onPlaySong = onPlaySong,
                                onDownloadSong = { onDownloadSong(track) },
                                isSelected = track.id == currentTrackId,
                                downloadsEnabled = downloadsEnabled,
                                downloadState = downloadStates[track.id] ?: DownloadState.None,
                                onRemoveSong = { onRemoveSong(track) },
                            )
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            contentColor = MaterialTheme.colors.primary,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        SongFilterBottomSheet(filterSheetState, filters, onApplyFilters, eventLogger)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TrackRowItemWrapper(
    track: NFTTrack,
    onPlaySong: (NFTTrack) -> Unit,
    onDownloadSong: () -> Unit,
    onRemoveSong: () -> Unit,
    isSelected: Boolean,
    downloadsEnabled: Boolean,
    downloadState: DownloadState,
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
                anchors = mapOf(0f to false, deltaX to true),
            ),
    ) {
        if (downloadsEnabled) {
            DownloadPanel(
                swipeableState = swipeableState,
                downloadState = downloadState,
                onRemoveSong = onRemoveSong,
                onDownloadSong = onDownloadSong,
            )
        }
        TrackRowItem(
            track = track,
            onClick = onPlaySong,
            modifier =
                if (downloadsEnabled) {
                    Modifier.offset {
                        IntOffset(x = -swipeableState.offset.value.roundToInt(), y = 0)
                    }
                } else {
                    Modifier
                },
            isSelected = isSelected,
            downloadState = downloadState,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun DownloadPanel(
    swipeableState: SwipeableState<Boolean>,
    downloadState: DownloadState,
    onRemoveSong: () -> Unit,
    onDownloadSong: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    fun closeSwipeView() {
        coroutineScope.launch { swipeableState.animateTo(false) }
    }

    RevealedPanel {
        if (
            downloadState is DownloadState.Completed || downloadState is DownloadState.Downloading
        ) {
            RemoveButton(
                onClick = {
                    closeSwipeView()
                    onRemoveSong()
                },
            )
        } else {
            DownloadButton(
                onClick = {
                    closeSwipeView()
                    onDownloadSong()
                },
            )
        }
    }
}

@Composable
private fun TrackRowItem(
    track: NFTTrack,
    onClick: (NFTTrack) -> Unit,
    modifier: Modifier,
    isSelected: Boolean,
    downloadState: DownloadState,
) {
    Row(
        modifier =
            modifier
                .background(color = Gray16)
                .clickable(onClick = { onClick(track) })
                .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(track.imageUrl)
                    .error(R.drawable.ic_default_track_cover_art)
                    .placeholder(R.drawable.ic_default_track_cover_art)
                    .build(),
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = track.title,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = if (isSelected) StatusGreen else White,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                when (downloadState) {
                    is DownloadState.Downloading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = StatusGreen,
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }

                    is DownloadState.Failed -> {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Failed",
                            tint = MaterialTheme.colors.error,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }

                    is DownloadState.Completed -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_downloaded),
                            contentDescription = stringResource(R.string.downloaded_description),
                            tint = StatusGreen,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                    }

                    DownloadState.None -> {
                        // Show nothing
                    }
                }

                Text(
                    text = track.artists.joinToString(","),
                    fontFamily = inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = GraySuit,
                )
            }
        }
    }
}

@Composable
fun RevealedPanel(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.background(Purple).fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        content()
    }
}

@Composable
fun DownloadButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(16.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = stringResource(R.string.library_download_description),
            )
        }
        Text(
            text = stringResource(id = R.string.library_download),
            style =
                TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = White,
                ),
        )
    }
}

@Composable
fun RemoveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(16.dp)) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.library_remove_description),
            )
        }
        Text(
            text = stringResource(id = R.string.library_remove_description),
            style =
                TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = White,
                ),
        )
    }
}

@Preview()
@Composable
fun PreviewNftLibrary() {
    NewmTheme(darkTheme = true) {
        NFTLibraryScreenUi(
            state =
                NFTLibraryState.Content(
                    nftTracks = emptyList(),
                    streamTokenTracks = emptyList(),
                    showZeroResultFound = false,
                    filters =
                        NFTLibraryFilters(
                            sortType = NFTLibrarySortType.None,
                            showShortTracks = false,
                        ),
                    refreshing = false,
                    downloadStates =
                        mapOf(
                            "track1" to DownloadState.Downloading(0.5f),
                            "track2" to DownloadState.Completed,
                            "track3" to DownloadState.Failed("Error message"),
                        ),
                    eventSink = {},
                    currentTrackId = null,
                    downloadsEnabled = true,
                ),
            eventLogger = NewmAppEventLogger(),
        )
    }
}

@Preview
@Composable
fun PreviewNftLibraryLoading() {
    NewmTheme(darkTheme = true) {
        NFTLibraryScreenUi(state = NFTLibraryState.Loading, eventLogger = NewmAppEventLogger())
    }
}

@Preview()
@Composable
fun PreviewNftLibraryEmptyWallet() {
    NewmTheme(darkTheme = true) {
        NFTLibraryScreenUi(state = NFTLibraryState.EmptyWallet, eventLogger = NewmAppEventLogger())
    }
}
