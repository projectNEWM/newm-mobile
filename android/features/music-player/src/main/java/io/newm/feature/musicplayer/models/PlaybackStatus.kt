package io.newm.feature.musicplayer.models

data class PlaybackStatus(
    val state: PlaybackState,
    val track: Track?,
    val position: Long,
    val duration: Long
) {
    companion object {
        val EMPTY: PlaybackStatus = PlaybackStatus(
            state = PlaybackState.STOPPED,
            position = 0,
            duration = 0,
            track = null,
        )
    }
}

enum class PlaybackState { PLAYING, PAUSED, BUFFERING, STOPPED }
