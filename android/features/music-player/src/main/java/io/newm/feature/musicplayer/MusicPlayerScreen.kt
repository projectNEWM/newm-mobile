package io.newm.feature.musicplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.feature.musicplayer.viewmodel.PlaybackUiEvent

@Composable
fun MusicPlayerScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    mediaPlayer: MusicPlayer? = rememberMediaPlayer(),
) {
    mediaPlayer ?: return
    val playbackStatus by mediaPlayer.playbackStatus.collectAsState()

    MusicPlayerViewer(
        modifier = modifier,
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
        },
        onSwipe = { direction ->
            with(mediaPlayer) {
                when (direction) {
                    1 -> next()  // Swipe right to next song
                    -1 -> previous()  // Swipe left to previous song
                }
            }
        }
    )
}
