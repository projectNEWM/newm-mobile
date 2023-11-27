package io.newm.feature.musicplayer.service

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import io.newm.feature.musicplayer.models.PlaybackRepeatMode
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.models.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MusicPlayer {
    val playbackStatus: StateFlow<PlaybackStatus>
    fun play()
    fun pause()
    fun stop()
    fun next()
    fun previous()
    fun seekTo(position: Long)
    fun repeat()
    fun setPlaylist(playlist: Playlist, initialTrackIndex: Int)
}

class MusicPlayerImpl(
    private val player: Player,
    scope: CoroutineScope
) : MusicPlayer {
    private val _playbackStatus = MutableStateFlow(PlaybackStatus.EMPTY)

    override val playbackStatus: StateFlow<PlaybackStatus>
        get() = _playbackStatus.asStateFlow()

    private var playlist: Playlist? = null

    init {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                updatePlaybackStatus()
            }

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                super.onPlayWhenReadyChanged(playWhenReady, reason)
                updatePlaybackStatus()
            }
        })
        scope.launch {
            while (true) {
                if (player.currentPosition != _playbackStatus.value.position) {
                    updatePlaybackStatus()
                }
                delay(500)
            }
        }
    }

    private fun updatePlaybackStatus() {
        _playbackStatus.update {
            val state = when (player.playbackState) {
                Player.STATE_BUFFERING -> PlaybackState.BUFFERING
                Player.STATE_READY -> if (player.playWhenReady) PlaybackState.PLAYING else PlaybackState.PAUSED
                else -> PlaybackState.STOPPED
            }
            val repeatMode = when(player.repeatMode) {
                Player.REPEAT_MODE_ALL -> PlaybackRepeatMode.REPEAT_ALL
                Player.REPEAT_MODE_ONE -> PlaybackRepeatMode.REPEAT_ONE
                else -> PlaybackRepeatMode.REPEAT_OFF
            }

            Log.d("MusicPlayer", "Updating playback status")
            PlaybackStatus(
                state = state,
                position = player.currentPosition,
                duration = player.duration,
                track = playlist?.tracks?.get(player.currentMediaItemIndex),
                repeatMode = repeatMode
            )
        }
    }

    override fun play() {
        player.play()
    }

    override fun pause() {
        Log.d("MusicPlayer", "Pause")
        player.pause()
    }

    override fun stop() {
        Log.d("MusicPlayer", "Stop")
        player.stop()
    }

    override fun next() {
        Log.d("MusicPlayer", "Next")
        player.seekToNext()
        play()
    }

    override fun previous() {
        Log.d("MusicPlayer", "Previous")
        player.seekToPrevious()
        play()
    }

    override fun repeat() {
        Log.d("MusicPlayer", "Repeat")
        player.repeatMode = when(player.repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        updatePlaybackStatus()
    }

    override fun seekTo(position: Long) {
        Log.d("MusicPlayer", "Seek to $position")
        player.seekTo(position)
    }

    override fun setPlaylist(playlist: Playlist, initialTrackIndex: Int) {
        Log.d("MusicPlayer", "Setting playlist with ${playlist.tracks.size} tracks")
        this.playlist = playlist

        player.setMediaItems(playlist.tracks.map { track ->
            MediaItem.Builder()
                .setUri(track.url)
                .setMediaId(track.id)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(track.title)
                        .setArtist(track.artist)
                        .build()
                )
                .build()
        })
        player.seekTo(initialTrackIndex, 0)
        player.prepare()
    }


}
