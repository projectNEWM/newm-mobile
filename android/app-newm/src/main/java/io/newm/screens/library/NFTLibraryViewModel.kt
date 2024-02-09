@file:OptIn(ExperimentalCoroutinesApi::class)

package io.newm.screens.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.feature.musicplayer.models.Playlist
import io.newm.feature.musicplayer.models.Track
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.usecases.ConnectWalletUseCase
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

@FlowPreview
class NFTLibraryViewModel(
    private val connectWalletUseCase: ConnectWalletUseCase,
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase,
    private val musicPlayer: MusicPlayer,
) : ViewModel() {
    private var playlist: Playlist = Playlist(emptyList())

    private val queryFlow = MutableStateFlow("")

    val state: StateFlow<NFTLibraryState> by lazy {
        connectWalletUseCase.isConnectedFlow().flatMapLatest { isConnected ->
            if (isConnected) {
                combine(
                    queryFlow.debounce(300),
                    walletNFTTracksUseCase.getAllStreamTokensFlow(),
                    walletNFTTracksUseCase.getAllNFTTracksFlow()
                ) { query, streamTokenTracks, nftTracks ->
                    setPlaylist(nftTracks + streamTokenTracks)
                    when {
                        nftTracks.isEmpty() && streamTokenTracks.isEmpty() -> {
                            NFTLibraryState.EmptyWallet
                        }

                        else -> {
                            NFTLibraryState.Content(
                                nftTracks = nftTracks.filter { it.matches(query) },
                                streamTokenTracks = streamTokenTracks.filter { it.matches(query) },
                                showZeroResultFound = query.isNotEmpty()
                                        && nftTracks.none { it.matches(query) }
                                        && streamTokenTracks.none { it.matches(query) }
                            )
                        }
                    }
                }.flowOn(Dispatchers.Default)
            } else {
                flowOf(NFTLibraryState.LinkWallet)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NFTLibraryState.Loading
        )
    }

    internal fun onQueryChange(newQuery: String) {
        queryFlow.update { newQuery }
    }

    fun onDownloadSong(tackId: String) {
        Log.e("NFTLibraryViewModel", "onDownloadSong: $tackId")
    }

    private suspend fun setPlaylist(nftTracks: List<NFTTrack>) = withContext(Dispatchers.Main) {
        requireNotNull(musicPlayer) { "Music player not initialized" }
        val playlistTracks = nftTracks.map { nftTrack ->
            Track(
                id = nftTrack.id,
                title = nftTrack.title,
                url = nftTrack.audioUrl,
                artist = nftTrack.artists.firstOrNull() ?: "",
                artworkUri = nftTrack.imageUrl,
            )
        }

        playlist = Playlist(playlistTracks)
        musicPlayer.setPlaylist(playlist, 0)
    }

    fun playSong(track: NFTTrack) {
        requireNotNull(musicPlayer) { "Music player not initialized" }

        Log.d("@@@", "playSong on mediaplayer $musicPlayer")
        val trackIndex = playlist.tracks.indexOfFirst { it.id == track.id }
        require(trackIndex >= 0) { "Track not found in playlist" }

        musicPlayer.apply {
            seekTo(trackIndex, 0)
            play()
        }
    }
}

sealed interface NFTLibraryState {
    data object Loading : NFTLibraryState
    data object LinkWallet : NFTLibraryState
    data object EmptyWallet : NFTLibraryState
    data class Content(
        val nftTracks: List<NFTTrack>,
        val streamTokenTracks: List<NFTTrack>,
        val showZeroResultFound: Boolean
    ) : NFTLibraryState

    data class Error(val message: String) : NFTLibraryState
}

fun NFTTrack.matches(query: String): Boolean {
    if (query.isBlank()) return true
    return (artists + title).any { it.contains(query, ignoreCase = true) }
}
