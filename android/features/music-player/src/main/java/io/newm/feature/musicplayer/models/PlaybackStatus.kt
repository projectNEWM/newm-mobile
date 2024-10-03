package io.newm.feature.musicplayer.models

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class PlaybackStatus(
    val state: PlaybackState,
    val track: Track?,
    val position: Long,
    val duration: Duration?,
    val repeatMode: PlaybackRepeatMode,
    val shuffleMode: Boolean
) {

    val elapsedFraction : Float get()  {
        return duration?.takeIf { it > 0.seconds }?.let { duration ->
           position.toFloat() / duration.inWholeMilliseconds
        } ?: 0f
    }
    companion object {
        val EMPTY: PlaybackStatus = PlaybackStatus(
            state = PlaybackState.STOPPED,
            position = 0,
            duration = null,
            track = null,
            repeatMode = PlaybackRepeatMode.REPEAT_OFF,
            shuffleMode = false,
        )
    }
}

enum class PlaybackState { PLAYING, PAUSED, BUFFERING, STOPPED }

enum class PlaybackRepeatMode { REPEAT_OFF, REPEAT_ONE, REPEAT_ALL }