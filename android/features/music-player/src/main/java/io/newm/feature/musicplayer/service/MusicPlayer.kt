package io.newm.feature.musicplayer.service

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.models.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface MusicPlayer {
    val playbackStatus: StateFlow<PlaybackStatus>
    fun play()
    fun pause()
    fun stop()
    fun next()
    fun previous()
    fun seekTo(position: Long)
    fun setPlaylist(playlist: Playlist, initialTrackIndex: Int)
}

class MusicPlayerImpl(
    private val player: Player,
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
    }

    private fun updatePlaybackStatus() {
        _playbackStatus.update {
            val state = when (player.playbackState) {
                Player.STATE_BUFFERING -> PlaybackState.BUFFERING
                Player.STATE_READY -> if (player.playWhenReady) PlaybackState.PLAYING else PlaybackState.PAUSED
                else -> PlaybackState.STOPPED
            }

            Log.d("MusicPlayer", "Updating playback status")
            PlaybackStatus(
                state = state,
                position = player.currentPosition,
                duration = player.duration,
                track = playlist?.tracks?.get(player.currentMediaItemIndex),
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
