package io.newm.feature.musicplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.models.Playlist
import io.newm.feature.musicplayer.models.Track
import io.newm.feature.musicplayer.repository.MusicRepository
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.usecases.WalletNFTTracksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MusicPlayerViewModel(
    private val musicPlayer: MusicPlayer,
    songId: String,
    musicRepository: MusicRepository,
    private val useCase: WalletNFTTracksUseCase
) : ViewModel() {
    private val songIdFlow: MutableStateFlow<String?> = MutableStateFlow(songId)

    private val _state: StateFlow<MusicPlayerState> by lazy {

        val songFlow: Flow<NFTTrack?> = songIdFlow.filterNotNull().map { trackId ->
            useCase.getNFTTrack(trackId)
        }
        combine(
            musicPlayer.playbackStatus,
            songFlow.mapNotNull { it }.onEach {
                musicPlayer.setPlaylist(
                    Playlist(
                        listOf(
                            Track(
                                id = it.id,
                                title = it.title,
                                url = it.audioUrl,
                                artist = it.artists.firstOrNull().orEmpty(),
                                artworkUri = it.imageUrl,
                            )
                        )
                    ),
                    initialTrackIndex = 0,
                )
            }
        ) { playbackStatus, song ->
            songIdFlow.update { playbackStatus.track?.id }

            MusicPlayerState.Content(
                song = song,
                playbackStatus = playbackStatus
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MusicPlayerState.Loading
        )

    }

    val state: StateFlow<MusicPlayerState>
        get() = _state

    fun onEvent(playbackUiEvent: PlaybackUiEvent) {
        when (playbackUiEvent) {
            PlaybackUiEvent.Next -> musicPlayer.next()
            PlaybackUiEvent.Pause -> musicPlayer.pause()
            PlaybackUiEvent.Play -> musicPlayer.play()
            PlaybackUiEvent.Previous -> musicPlayer.previous()
            PlaybackUiEvent.Repeat -> musicPlayer.repeat()
            is PlaybackUiEvent.Seek -> musicPlayer.seekTo(playbackUiEvent.position)
        }
    }
}

sealed interface MusicPlayerState {
    data object Loading : MusicPlayerState
    data class Content(val song: NFTTrack, val playbackStatus: PlaybackStatus) : MusicPlayerState
}
