package io.newm.feature.musicplayer.viewmodel

sealed interface PlaybackUiEvent {
    data object Play : PlaybackUiEvent
    data object Pause : PlaybackUiEvent
    data object Next : PlaybackUiEvent
    data object Previous : PlaybackUiEvent

    data class Seek(val position: Long): PlaybackUiEvent

    data object Repeat : PlaybackUiEvent
    data object ToggleShuffle : PlaybackUiEvent
}
