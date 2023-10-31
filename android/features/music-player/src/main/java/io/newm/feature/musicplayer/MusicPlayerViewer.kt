package io.newm.feature.musicplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.core.theme.Gray100
import io.newm.core.theme.White
import io.newm.core.theme.inter
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
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
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
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "Playing from your NFT library".toUpperCase(Locale.current),
                fontFamily = inter,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Gray100,
            )
            AsyncImage(
                model = song.coverArtUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = song.title,
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = White
            )
            Text(
                text = song.ownerId,
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Gray100
            )
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
        Image(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = onClick),
            painter = painterResource(R.drawable.music_player_ic_previous),
            contentDescription = "Previous Song"
        )
    }


}

@Composable
private fun PlayButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Image(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = onClick),
            painter = painterResource(R.drawable.music_player_ic_play),
            contentDescription = "Play"
        )
    }

}

@Composable
fun PauseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Image(
            modifier = Modifier
                .wrapContentSize()
                .clickable(onClick = onClick),
            painter = painterResource(R.drawable.music_player_ic_pause),
            contentDescription = "Pause",
        )
    }
}

@Composable
fun NextTrackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(modifier = modifier, onClick = onClick) {
        Image(
            modifier = Modifier
                .wrapContentSize(),
            painter = painterResource(R.drawable.music_player_ic_next),
            contentDescription = "Next Song"
        )
    }

}

@Composable
private fun ShuffleIcon() {
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(x = 28.dp, y = (-14).dp),
        painter = painterResource(R.drawable.music_player_ic_shuffle),
        contentDescription = "Shuffle"
    )
}

