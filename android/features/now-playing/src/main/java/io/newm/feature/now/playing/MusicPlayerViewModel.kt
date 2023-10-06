package io.newm.feature.now.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.newm.shared.models.Song
import io.newm.shared.usecases.WalletNFTSongsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class MusicPlayerViewModel(private val useCase: WalletNFTSongsUseCase): ViewModel(){
    private val songIdFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    private var _state: StateFlow<MusicPlayerState> = songIdFlow.filterNotNull().flatMapLatest { songId ->
        useCase.getAllWalletNFTSongs(songId).mapLatest {
            val songMap: Map<String, Song> = it.associateBy { song -> song.id }
            val songToPlay = songMap[songId]
            MusicPlayerState.Content(song = songToPlay?: it.first())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = MusicPlayerState.Loading
    )

    val state: StateFlow<MusicPlayerState>
        get() = _state

    fun setSongId(songId: String) {
        this.songIdFlow.value = songId
    }
}

sealed interface MusicPlayerState {
    data object Loading : MusicPlayerState
    data class Content(val song: Song) : MusicPlayerState
}