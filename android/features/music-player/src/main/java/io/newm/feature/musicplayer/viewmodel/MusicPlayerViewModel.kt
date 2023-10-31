package io.newm.feature.musicplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.repository.MusicRepository
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.shared.models.Song
import io.newm.shared.usecases.WalletNFTSongsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

class MusicPlayerViewModel(
    private val musicPlayer: MusicPlayer,
    songId: String,
    musicRepository: MusicRepository,
    private val useCase: WalletNFTSongsUseCase
) : ViewModel() {
    private val songIdFlow: MutableStateFlow<String?> = MutableStateFlow(songId)

    private val _state: StateFlow<MusicPlayerState> by lazy {
        val songFlow = songIdFlow.flatMapLatest { songId ->
            useCase.getAllWalletNFTSongs().map { songs ->
                val songMap: Map<String, Song> = songs.associateBy { song -> song.id }
                val songToPlay = songMap[songId]
                songToPlay ?: songs.first()
            }
        }
        combine(
            musicPlayer.playbackStatus,
            songFlow
        ) { playbackStatus, song ->
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
