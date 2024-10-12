package io.newm.feature.musicplayer.service

import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import io.newm.feature.musicplayer.models.PlaybackRepeatMode
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.models.Playlist
import io.newm.feature.musicplayer.models.Track
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

interface MusicPlayer {
    val playbackStatus: StateFlow<PlaybackStatus>
    fun play()
    fun pause()
    fun stop()
    fun next()
    fun previous()
    fun seekTo(position: Long)
    fun seekTo(index: Int, position: Long)
    fun repeat()
    fun setPlaylist(playlist: Playlist, initialTrackIndex: Int)
    fun toggleShuffle()
}

class MusicPlayerImpl(
    private val player: Player,
    scope: CoroutineScope,
    private val eventLogger: NewmAppEventLogger
) : MusicPlayer {
    private val _playbackStatus = MutableStateFlow(PlaybackStatus.EMPTY)

    override val playbackStatus: StateFlow<PlaybackStatus>
        get() = _playbackStatus.asStateFlow()

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
                Player.STATE_READY -> {
                    if (player.playWhenReady) PlaybackState.PLAYING else PlaybackState.PAUSED
                }
                else -> PlaybackState.STOPPED
            }
            val repeatMode = when (player.repeatMode) {
                Player.REPEAT_MODE_ALL -> PlaybackRepeatMode.REPEAT_ALL
                Player.REPEAT_MODE_ONE -> PlaybackRepeatMode.REPEAT_ONE
                else -> PlaybackRepeatMode.REPEAT_OFF
            }

            PlaybackStatus(
                state = state,
                position = player.currentPosition,
                duration = if(player.duration == C.TIME_UNSET ) null else player.duration.milliseconds,
                track = player.currentMediaItem?.toTrack(),
                repeatMode = repeatMode,
                shuffleMode = player.shuffleModeEnabled
            )
        }
    }

    override fun play() {
        eventLogger.logClickEvent(AppScreens.MusicPlayerScreen.PLAY_BUTTON)
        player.play()
    }

    override fun pause() {
        eventLogger.logClickEvent(AppScreens.MusicPlayerScreen.PAUSE_BUTTON)
        Log.d("MusicPlayer", "Pause")
        player.pause()
    }

    override fun stop() {
        eventLogger.logClickEvent(AppScreens.MusicPlayerScreen.STOP_BUTTON)
        Log.d("MusicPlayer", "Stop")
        player.stop()
    }

    override fun next() {
        eventLogger.logClickEvent(AppScreens.MusicPlayerScreen.NEXT_BUTTON)
        Log.d("MusicPlayer", "Next")
        player.seekToNext()
        play()
    }

    override fun previous() {
        eventLogger.logClickEvent(AppScreens.MusicPlayerScreen.PREVIOUS_BUTTON)
        Log.d("MusicPlayer", "Previous")
        player.seekToPrevious()
        play()
    }

    override fun repeat() {
        eventLogger.logClickEvent(AppScreens.MusicPlayerScreen.REPEAT_BUTTON)
        Log.d("MusicPlayer", "Repeat")
        player.repeatMode = when (player.repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        updatePlaybackStatus()
    }

    override fun seekTo(position: Long) {
        eventLogger.logEvent(AppScreens.MusicPlayerScreen.SEEK_ACTION, mapOf("position" to position))
        Log.d("MusicPlayer", "Seek to $position")
        player.seekTo(position)
    }

    override fun seekTo(index: Int, position: Long) {
        eventLogger.logEvent(AppScreens.MusicPlayerScreen.SEEK_ACTION, mapOf("position" to position, "index" to index))
        Log.d("MusicPlayer", "Seek to $index, $position")
        player.seekTo(index, position)
    }

    override fun setPlaylist(playlist: Playlist, initialTrackIndex: Int) {
        eventLogger.logEvent(AppScreens.NFTLibraryScreen.PLAYLIST_SIZE_EVENT, mapOf("size" to playlist.tracks.size))
        Log.d("MusicPlayer", "Setting playlist with ${playlist.tracks.size} tracks")
        player.setMediaItems(playlist.tracks.map { track ->
            MediaItem.Builder()
                .setUri(track.url)
                .setMediaId(track.id)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(track.title)
                        .setArtist(track.artist)
                        .setArtworkUri(track.artworkUri?.toUri())
                        .build()
                )
                .build()
        })
        player.seekTo(initialTrackIndex, 0)
    }

    override fun toggleShuffle() {
        eventLogger.logClickEvent(AppScreens.MusicPlayerScreen.TOGGLE_SHUFFLE_BUTTON)
        Log.d("MusicPlayer", "Toggle shuffle")
        player.shuffleModeEnabled = !player.shuffleModeEnabled
    }
}

private fun MediaItem.toTrack(): Track {
    return Track(
        id = mediaId,
        title = mediaMetadata.title.toString(),
        artist = mediaMetadata.artist.toString(),
        url = requestMetadata.mediaUri.toString(),
        artworkUri = mediaMetadata.artworkUri?.toString()
    )
}
