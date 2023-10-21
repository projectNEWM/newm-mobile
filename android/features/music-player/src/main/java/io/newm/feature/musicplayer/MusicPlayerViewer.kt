package io.newm.feature.musicplayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PauseCircleOutline
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.viewmodel.PlaybackUiEvent
import io.newm.shared.models.Song

@Composable
internal fun MusicPlayerViewer(
    song: Song,
    onNavigateUp: () -> Unit,
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit,
) {
    Box {
        AsyncImage(
            model = song.coverArtUrl,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "Like",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Overflow",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            PlayerControllers(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                playbackStatus = playbackStatus,
                onEvent = onEvent
            )
        }
    }
}


@Composable
fun PlayerControllers(
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
    ) {
        PreviousTrackButton(onClick = { onEvent(PlaybackUiEvent.Previous) })
        PlayOrPauseButton(playbackStatus = playbackStatus, onEvent = onEvent)
        NextTrackButton(onClick = { onEvent(PlaybackUiEvent.Next) })
    }
}

@Composable
private fun PlayOrPauseButton(
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit
) {
    when (playbackStatus.state) {
        PlaybackState.PLAYING, PlaybackState.BUFFERING -> {
            PauseButton(onClick = {
                onEvent(
                    PlaybackUiEvent.Pause
                )
            })
        }

        PlaybackState.PAUSED, PlaybackState.STOPPED -> {
            PlayButton(onClick = {
                onEvent(
                    PlaybackUiEvent.Play
                )
            })
        }
    }
}

@Composable
private fun PreviousTrackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.SkipPrevious,
            contentDescription = "Skip previous",
            tint = Color.White
        )
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.PlayCircleOutline,
            contentDescription = "Play",
            tint = Color.White
        )
    }
}

@Composable
fun PauseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.PauseCircleOutline,
            contentDescription = "Pause",
            tint = Color.White
        )
    }
}

@Composable
fun NextTrackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.SkipNext,
            contentDescription = "Skip next",
            tint = Color.White
        )
    }
}

