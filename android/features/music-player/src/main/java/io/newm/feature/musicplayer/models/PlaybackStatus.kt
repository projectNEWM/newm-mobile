package io.newm.feature.musicplayer.models

data class PlaybackStatus(
    val state: PlaybackState,
    val track: Track?,
    val position: Long,
    val duration: Long,
    val repeatMode: PlaybackRepeatMode
) {
    companion object {
        val EMPTY: PlaybackStatus = PlaybackStatus(
            state = PlaybackState.STOPPED,
            position = 0,
            duration = 0,
            track = null,
            repeatMode = PlaybackRepeatMode.REPEAT_OFF
        )
    }
}

enum class PlaybackState { PLAYING, PAUSED, BUFFERING, STOPPED }

enum class PlaybackRepeatMode { REPEAT_OFF, REPEAT_ONE, REPEAT_ALL }