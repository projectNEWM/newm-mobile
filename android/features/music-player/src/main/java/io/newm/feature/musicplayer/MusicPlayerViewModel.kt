package io.newm.feature.musicplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.newm.shared.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class MusicPlayerViewModel() : ViewModel() {
    private val songIdFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    private var _state: StateFlow<MusicPlayerState> =
        songIdFlow.filterNotNull().mapLatest { songId ->
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
            MusicPlayerState.Content(song)
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