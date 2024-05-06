package io.newm.screens.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.feature.musicplayer.models.Playlist
import io.newm.feature.musicplayer.models.Track
import io.newm.feature.musicplayer.rememberMediaPlayer
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NFTLibraryPresenter(
    private val navigator: Navigator,
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase,
    private val scope: CoroutineScope,
) : Presenter<NFTLibraryState> {
    @Composable
    override fun present(): NFTLibraryState {
        val musicPlayer: MusicPlayer? = rememberMediaPlayer()

        val isWalletConnected: Boolean? by remember { connectWalletUseCase.hasWalletConnectionsFlow() }.collectAsState(
            null
        )

        var query by rememberSaveable { mutableStateOf("") }

        val nftTracks by remember(isWalletConnected) {
            if (isWalletConnected == true) {
                walletNFTTracksUseCase.getAllNFTTracksFlow()
            } else {
                flowOf()
            }
        }.collectAsState(initial = emptyList())

        val streamTracks by remember(isWalletConnected) {
            if (isWalletConnected == true) {
                walletNFTTracksUseCase.getAllStreamTokensFlow()
            } else {
                flowOf()
            }
        }.collectAsState(initial = emptyList())

        val playList = remember(
            nftTracks,
            streamTracks
        ) { Playlist(nftTracks.toTrack() + streamTracks.toTrack()) }

        var filters: NFTLibraryFilters by remember {
            mutableStateOf(
                NFTLibraryFilters(
                    sortType = NFTLibrarySortType.None,
                    showShortTracks = false
                )
            )
        }

        return when {
            isWalletConnected == null -> NFTLibraryState.Loading
            isWalletConnected == false -> NFTLibraryState.LinkWallet { newmWalletConnectionId ->
                scope.launch {
                    connectWalletUseCase.connect(newmWalletConnectionId)
                }
            }

            nftTracks.isEmpty() && streamTracks.isEmpty() -> NFTLibraryState.EmptyWallet

            else -> NFTLibraryState.Content(
                nftTracks = nftTracks.filterAndSort(query, filters),
                streamTokenTracks = streamTracks.filterAndSort(query, filters),
                showZeroResultFound = query.isNotEmpty()
                        && nftTracks.none { it.matches(query) }
                        && streamTracks.none { it.matches(query) },
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