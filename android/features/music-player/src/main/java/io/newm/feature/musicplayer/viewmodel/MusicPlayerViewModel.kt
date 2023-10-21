package io.newm.feature.musicplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.repository.MusicRepository
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.shared.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MusicPlayerViewModel(
    private val musicPlayer: MusicPlayer,
    songId: String,
    musicRepository: MusicRepository,
) : ViewModel() {
    private val songIdFlow: MutableStateFlow<String?> = MutableStateFlow(songId)

    private var _state: StateFlow<MusicPlayerState> =
        combine(
            songIdFlow,
            musicPlayer.playbackStatus,
        ) { songId, playbackStatus ->
            if (songId == null) {
                MusicPlayerState.Loading
            } else {
                val song = Song( //TODO: Replace hard coded values
                    id = songId,
                    ownerId = "",
                    createdAt = "",
                    title = "",
                    genres = emptyList(),
                    mintingStatus = "",
                    marketplaceStatus = "",
                    coverArtUrl = "https://images.unsplash.com/photo-1589405858862-2ac9cbb41321?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8M3x8fGVufDB8fHx8fA%3D%3D&w=1000&q=80"
                )
                MusicPlayerState.Content(
                    song = song,
                    playbackStatus = playbackStatus
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MusicPlayerState.Loading
        )

    val state: StateFlow<MusicPlayerState>
        get() = _state

    init {
        musicPlayer.setPlaylist(musicRepository.fetchPlaylist("test"), songId.toIntOrNull() ?: 0)
        onEvent(PlaybackUiEvent.Play)
    }

    fun onEvent(playbackUiEvent: PlaybackUiEvent) {
        when (playbackUiEvent) {
            PlaybackUiEvent.Next -> musicPlayer.next()
            PlaybackUiEvent.Pause -> musicPlayer.pause()
            PlaybackUiEvent.Play -> musicPlayer.play()
            PlaybackUiEvent.Previous -> musicPlayer.previous()
        }
    }
}

sealed interface MusicPlayerState {
    data object Loading : MusicPlayerState
    data class Content(val song: Song, val playbackStatus: PlaybackStatus) : MusicPlayerState
}
