package io.newm.feature.musicplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.feature.musicplayer.viewmodel.PlaybackUiEvent

@Composable
fun MusicPlayerScreen(
    onNavigateUp: () -> Unit,
    mediaPlayer: MusicPlayer? = rememberMediaPlayer(),
) {
    mediaPlayer ?: return
    val playbackStatus by mediaPlayer.playbackStatus.collectAsState()

    MusicPlayerViewer(
        onNavigateUp = onNavigateUp,
        playbackStatus = playbackStatus,
        onEvent = { event ->
            with(mediaPlayer) {
                when (event) {
                    PlaybackUiEvent.Next -> next()
                    PlaybackUiEvent.Pause -> pause()
                    PlaybackUiEvent.Play -> play()
                    PlaybackUiEvent.Previous -> previous()
                    PlaybackUiEvent.Repeat -> repeat()
                    is PlaybackUiEvent.Seek -> seekTo(event.position)
                }
            }
        }
    )
}
