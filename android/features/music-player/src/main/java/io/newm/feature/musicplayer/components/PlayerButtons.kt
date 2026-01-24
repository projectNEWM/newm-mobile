package io.newm.feature.musicplayer.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.newm.core.resources.R
import io.newm.core.ui.theme.DarkPink
import io.newm.core.ui.theme.DarkViolet
import io.newm.core.ui.theme.Gray500
import io.newm.core.ui.theme.White
import io.newm.core.ui.utils.drawWithBrush
import io.newm.feature.musicplayer.models.PlaybackRepeatMode
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.viewmodel.PlaybackUiEvent

internal val MusicPlayerBrush = Brush.horizontalGradient(listOf(DarkViolet, DarkPink))

@Composable
fun PlayOrPauseButton(
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit,
) {
    when (playbackStatus.state) {
        PlaybackState.PLAYING,
        PlaybackState.BUFFERING,
        -> {
            PauseButton(onClick = { onEvent(PlaybackUiEvent.Pause) })
        }

        PlaybackState.PAUSED,
        PlaybackState.STOPPED,
        -> {
            PlayButton(onClick = { onEvent(PlaybackUiEvent.Play) })
        }
    }
}

@Composable
fun ShuffleButton(
    onClick: () -> Unit,
    shuffleMode: Boolean,
    enabled: Boolean,
) {
    IconButton(onClick = onClick, enabled = enabled) {
        Icon(
            painter = painterResource(R.drawable.ic_music_player_shuffle),
            contentDescription = stringResource(R.string.music_player_shuffle_description),
            tint =
                when {
                    enabled.not() -> Gray500
                    shuffleMode -> DarkViolet
                    else -> White
                },
        )
    }
}

@Composable
fun PreviousTrackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_prev_track_default),
            contentDescription = stringResource(R.string.music_player_prev_track_description),
            tint = Color.White,
        )
    }
}

@Composable
fun PlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = stringResource(R.string.music_player_play_description),
            modifier = Modifier.drawWithBrush(MusicPlayerBrush),
        )
    }
}

@Composable
fun PauseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pause),
            contentDescription = stringResource(R.string.music_player_pause_description),
            modifier = Modifier.drawWithBrush(MusicPlayerBrush),
        )
    }
}

@Composable
fun NextTrackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_next_track_default),
            contentDescription = stringResource(R.string.music_player_next_track_description),
            tint = Color.White,
        )
    }
}

@Composable
fun RepeatButton(
    repeatMode: PlaybackRepeatMode,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val imageRes =
        when (repeatMode) {
            PlaybackRepeatMode.REPEAT_OFF -> R.drawable.ic_repeat_off
            PlaybackRepeatMode.REPEAT_ONE -> R.drawable.ic_music_player_repeat_one
            PlaybackRepeatMode.REPEAT_ALL -> R.drawable.ic_music_player_repeat_all
        }
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = imageRes),
            contentDescription = stringResource(R.string.music_player_repeat_description),
            tint = if (repeatMode == PlaybackRepeatMode.REPEAT_OFF) White else DarkViolet,
        )
    }
}
