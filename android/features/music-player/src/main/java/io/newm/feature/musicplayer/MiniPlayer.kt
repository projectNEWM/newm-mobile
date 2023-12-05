package io.newm.feature.musicplayer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import io.newm.core.theme.Gray500
import io.newm.core.theme.White
import io.newm.feature.musicplayer.models.PlaybackState
import io.newm.feature.musicplayer.models.PlaybackStatus
import io.newm.feature.musicplayer.models.Track
import io.newm.feature.musicplayer.service.MusicPlayer


@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    mediaPlayer: MusicPlayer? = rememberMediaPlayer(),
) {
    mediaPlayer ?: return
    val playbackStatus by mediaPlayer.playbackStatus.collectAsState()

    MiniPlayer(
        modifier = modifier,
        onPlayPauseClicked = {
            when (playbackStatus.state) {
                PlaybackState.PLAYING,
                PlaybackState.BUFFERING -> mediaPlayer.pause()

                PlaybackState.PAUSED,
                PlaybackState.STOPPED -> mediaPlayer.play()
            }
        },
        playStatus = playbackStatus
    )
}


@Composable
fun MiniPlayer(
    onPlayPauseClicked: () -> Unit,
    playStatus: PlaybackStatus,
    modifier: Modifier = Modifier
) {
    val artistName = playStatus.track?.title.orEmpty()
    val songTitle = playStatus.track?.artist.orEmpty()
    val artworkUrl = playStatus.track?.artworkUri

    AnimatedVisibility(
        visible = playStatus.track != null,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Card(
            modifier = modifier,
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.background,
        ) {
            Column {
                MusicPlayerSlider(
                    value = if (playStatus.duration == 0L) 0f else playStatus.position.toFloat() / playStatus.duration.toFloat(),
                    onValueChange = {},
                    colors = SliderDefaults.colors(
                        thumbColor = White,
                        inactiveTrackColor = Gray500
                    ),
                    allowScrub = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    artworkUrl?.let {
                        Image(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(shape = RoundedCornerShape(size = 4.dp)),
                            painter = rememberAsyncImagePainter(model = it),
                            contentDescription = "artwork",
                            contentScale = ContentScale.FillBounds,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Column {
                        Text(
                            text = songTitle,
                            style = MaterialTheme.typography.body2
                        )
                        Text(
                            text = artistName,
                            style = MaterialTheme.typography.body2.copy(
                                fontSize = 10.sp,
                                color = Color(0xFF8F8F91)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { onPlayPauseClicked() }) {
                        AnimatedContent(
                            targetState = playStatus.state,
                            label = "PlayPauseButton"
                        ) { state ->
                            when (state) {
                                PlaybackState.PAUSED,
                                PlaybackState.STOPPED -> {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                    )
                                }

                                PlaybackState.PLAYING,
                                PlaybackState.BUFFERING -> {
                                    Icon(
                                        Icons.Default.Pause,
                                        contentDescription = "Pause",
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMiniPlayer() {
    MiniPlayer(
        playStatus = PlaybackStatus.EMPTY.copy(
            track = Track(
                id = "1",
                title = "Song Title",
                artist = "Artist Name",
                url = ""
            )
        ),
        onPlayPauseClicked = {}
    )
}
