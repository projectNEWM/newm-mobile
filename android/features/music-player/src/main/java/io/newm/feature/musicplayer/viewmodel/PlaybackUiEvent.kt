package io.newm.feature.musicplayer.viewmodel

sealed interface PlaybackUiEvent {
    data object Play : PlaybackUiEvent
    data object Pause : PlaybackUiEvent
    data object Next : PlaybackUiEvent
    data object Previous : PlaybackUiEvent
}
