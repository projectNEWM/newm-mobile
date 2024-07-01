package io.newm.screens.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.musicplayer.models.Playlist
import io.newm.feature.musicplayer.models.Track
import io.newm.feature.musicplayer.rememberMediaPlayer
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.HasWalletConnectionsUseCase
import io.newm.shared.public.usecases.SyncWalletConnectionsUseCase
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class NFTLibraryPresenter(
    private val navigator: Navigator,
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase,
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase,
    private val scope: CoroutineScope,
) : Presenter<NFTLibraryState> {
    @Composable
    override fun present(): NFTLibraryState {
        val musicPlayer: MusicPlayer? = rememberMediaPlayer()

        LaunchedEffect(Unit) {
            syncWalletConnectionsUseCase.syncWalletConnectionsFromNetworkToDevice()
        }

        val isWalletConnected: Boolean? by remember { hasWalletConnectionsUseCase.hasWalletConnectionsFlow() }.collectAsRetainedState(
            null
        )

        val hasEmptyWallet by remember { walletNFTTracksUseCase.hasEmptyWallet }.collectAsState(false)

        var query by rememberSaveable { mutableStateOf("") }

        if (isWalletConnected == true) {
            LaunchedEffect(Unit) {
                walletNFTTracksUseCase.refresh()
            }
        }

        val nftTracks by remember(isWalletConnected) {
            if (isWalletConnected == true) {
                walletNFTTracksUseCase.getAllCollectableTracksFlow()
            } else {
                flowOf()
            }
        }.collectAsRetainedState(initial = emptyList())

        val streamTracks by remember(isWalletConnected) {
            if (isWalletConnected == true) {
                walletNFTTracksUseCase.getAllStreamTokensFlow()
            } else {
                flowOf()
            }
        }.collectAsRetainedState(initial = emptyList())

        val playList = remember(
            nftTracks,
            streamTracks
        ) { Playlist(nftTracks.toTrack() + streamTracks.toTrack()) }

        var filters: NFTLibraryFilters by rememberRetained {
            mutableStateOf(
                NFTLibraryFilters(
                    sortType = NFTLibrarySortType.None,
                    showShortTracks = false
                )
            )
        }

        val filteredNftTracks = remember(nftTracks, query, filters) {
            nftTracks.filterAndSort(query, filters)
        }

        val filteredStreamTokens = remember(nftTracks, query, filters) {
            streamTracks.filterAndSort(query, filters)
        }

        val isLoading = isWalletConnected == null || (nftTracks.isEmpty() && streamTracks.isEmpty())

        val showZeroResultFound = remember(query, nftTracks, streamTracks) {
            (query.isNotEmpty()
                    && nftTracks.none { it.matches(query) }
                    && streamTracks.none { it.matches(query) })
        }

        return when {
            hasEmptyWallet -> NFTLibraryState.EmptyWallet
            isLoading -> NFTLibraryState.Loading
            isWalletConnected == false -> NFTLibraryState.LinkWallet { newmWalletConnectionId ->
                scope.launch {
                    connectWalletUseCase.connect(newmWalletConnectionId)
                }
            }
            else -> {
                NFTLibraryState.Content(
                    nftTracks = filteredNftTracks,
                    streamTokenTracks = filteredStreamTokens,
                    showZeroResultFound = showZeroResultFound,
                    filters = filters,
                    eventSink = { event ->
                        when (event) {
                            is NFTLibraryEvent.OnDownloadTrack -> TODO("Not implemented yet")
                            is NFTLibraryEvent.OnQueryChange -> query = event.newQuery
                            is NFTLibraryEvent.PlaySong -> {
                                val trackIndex =
                                    playList.tracks.indexOfFirst { it.id == event.track.id }

                                require(trackIndex >= 0) { "Track not found in playlist" }

                                musicPlayer?.apply {
                                    setPlaylist(playList, trackIndex)
                                    play()
                                }
                            }

                            is NFTLibraryEvent.OnApplyFilters -> filters = event.filters
                        }
                    }
                )
            }
        }
    }

    private fun List<NFTTrack>.filterAndSort(
        query: String,
        filters: NFTLibraryFilters
    ): List<NFTTrack> {
        val filteredTracks = filter {
            it.matches(query) && (filters.showShortTracks || it.duration >= 30)
        }
        return when (filters.sortType) {
            NFTLibrarySortType.None -> filteredTracks
            NFTLibrarySortType.ByTitle -> filteredTracks.sortedBy { it.title }
            NFTLibrarySortType.ByArtist -> filteredTracks.sortedBy { it.artists.first() }
            NFTLibrarySortType.ByLength -> filteredTracks.sortedBy { it.duration }
        }
    }
}

private fun NFTTrack.matches(query: String): Boolean {
    if (query.isBlank()) return true
    return (artists + title).any { it.contains(query, ignoreCase = true) }
}


private fun List<NFTTrack>.toTrack(): List<Track> = map { nftTrack ->
    Track(
        id = nftTrack.id,
        title = nftTrack.title,
        url = nftTrack.audioUrl,
        artist = nftTrack.artists.firstOrNull() ?: "",
        artworkUri = nftTrack.imageUrl,
    )
}
