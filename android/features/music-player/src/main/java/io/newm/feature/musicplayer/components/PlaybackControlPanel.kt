package io.newm.feature.musicplayer.components

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.ui.theme.Black
import io.newm.core.ui.theme.Gray500
import io.newm.core.ui.theme.GraySuit
import io.newm.core.ui.theme.White
import io.newm.core.ui.utils.millisToMinutesSecondsString
import io.newm.feature.musicplayer.MusicPlayerSlider
import io.newm.feature.musicplayer.SliderDefaults
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.viewmodel.PlaybackUiEvent

private val playbackTimeStyle
    @Composable
    get() =
        TextStyle(
            fontSize = 12.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            color = GraySuit,
        )

@Composable
fun MusicPlayerControls(
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit,
) {
    Box {
        PlaybackControlPanel(playbackStatus = playbackStatus, onEvent = onEvent)
        if (playbackStatus.duration != null) {
            MusicPlayerSlider(
                value = playbackStatus.elapsedFraction,
                onValueChange = {
                    onEvent(
                        PlaybackUiEvent.Seek(
                            (it * playbackStatus.duration.inWholeMilliseconds).toLong(),
                        ),
                    )
                },
                colors = SliderDefaults.colors(thumbColor = White, inactiveTrackColor = Gray500),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp).height(4.dp),
            )
        }
    }
}

@Composable
fun PlaybackControlPanel(
    playbackStatus: PlaybackStatus,
    onEvent: (PlaybackUiEvent) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .height(102.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding()
                .clip(shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)),
    ) {
        Column(modifier = Modifier.background(Black).fillMaxSize()) {
            Row(modifier = Modifier.padding(horizontal = 12.dp).padding(top = 8.dp)) {
                Text(
                    text = playbackStatus.position.millisToMinutesSecondsString(),
                    style = playbackTimeStyle,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text =
                        if (playbackStatus.duration == null) {
                            "-:--"
                        } else {
                            playbackStatus.duration.inWholeMilliseconds
                                .millisToMinutesSecondsString()
                        },
                    style = playbackTimeStyle,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.padding(12.dp)) {
                RepeatButton(
                    playbackStatus.repeatMode,
                    onClick = { onEvent(PlaybackUiEvent.Repeat) },
                )
                Spacer(modifier = Modifier.weight(1f))
                PreviousTrackButton(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onClick = { onEvent(PlaybackUiEvent.Previous) },
                )
                PlayOrPauseButton(playbackStatus = playbackStatus, onEvent = onEvent)
                NextTrackButton(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    onClick = { onEvent(PlaybackUiEvent.Next) },
                )
                Spacer(modifier = Modifier.weight(1f))
                ShuffleButton(
                    enabled = playbackStatus.state == PlaybackState.PLAYING,
                    shuffleMode = playbackStatus.shuffleMode,
                    onClick = { onEvent(PlaybackUiEvent.ToggleShuffle) },
                )
            }
        }
    }
}
