package io.newm.feature.musicplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.core.theme.Black
import io.newm.core.theme.DarkPink
import io.newm.core.theme.DarkViolet
import io.newm.core.theme.Gray23
import io.newm.core.theme.Gray500
import io.newm.core.theme.GraySuit
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.ui.utils.iconGradient
import io.newm.core.ui.utils.millisToMinutesSecondsString
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.viewmodel.PlaybackUiEvent
import io.newm.shared.models.Song

private val playbackTimeStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Normal,
    color = GraySuit
)

private val LibraryIconGradient = iconGradient(DarkViolet, DarkPink)

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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.music_player_back),
                        contentDescription = "Back",
                        tint = White
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = song.title,
                color = White,
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
            Text(
                //text = song., //artist??
                modifier = Modifier.padding(top = 4.dp, bottom = 28.dp),
                text = "Artist Name",
                color = White,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
            Box {
                PlaybackControlPanel(
                    playbackStatus = playbackStatus,
                    onEvent = onEvent
                )
                Slider(
                    value = playbackStatus.position.toFloat()/playbackStatus.duration.toFloat(),
                    onValueChange = { onEvent(PlaybackUiEvent.Seek((it * playbackStatus.duration).toLong())) },
                    colors = SliderDefaults.colors(
                        thumbColor = White,
                        activeTrackColor = DarkPink,
                        inactiveTrackColor = Gray500
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(4.dp)

                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun PlaybackControlPanel(
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .height(102.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding()
            .clip(
                shape = RoundedCornerShape(
                    topStart = 2.dp,
                    topEnd = 2.dp,
                    bottomEnd = 8.dp,
                    bottomStart = 8.dp
                )
            )
    ) {
        Column(
            modifier = Modifier
                .background(Black)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = playbackStatus.position.millisToMinutesSecondsString(),
                    style = playbackTimeStyle
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = playbackStatus.duration.millisToMinutesSecondsString(),
                    style = playbackTimeStyle
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.padding(12.dp)) {
                RepeatButton(onClick = {})
                Spacer(modifier = Modifier.weight(1f))
                PreviousTrackButton(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onClick = { onEvent(PlaybackUiEvent.Previous) })
                PlayOrPauseButton(playbackStatus = playbackStatus, onEvent = onEvent)
                NextTrackButton(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onClick = { onEvent(PlaybackUiEvent.Next) })
                Spacer(modifier = Modifier.weight(1f))
                ShareButton(onClick = {})
            }
        }
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
            painter = painterResource(id = R.drawable.ic_prev_track_default),
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
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = "Play",
            tint = DarkPink
        )
    }
}

@Composable
fun PauseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pause),
            contentDescription = "Pause",
            tint = DarkPink
        )
    }
}

@Composable
fun NextTrackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_next_track_default),
            contentDescription = "Skip next",
            tint = Color.White
        )
    }
}

@Composable
fun ShareButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = "Share Song",
            tint = Color.White
        )
    }
}

@Composable
fun RepeatButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_repeat_default),
            contentDescription = "Repeat",
            tint = Gray23
        )
    }
}

