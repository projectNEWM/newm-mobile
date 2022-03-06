package io.projectnewm.feature.now.playing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun NowPlayingScreen() {
    ArtistBackgroundImage()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SongName()

        Spacer(modifier = Modifier.height(16.dp))

        ArtistName()

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.wrapContentSize()) {
            SharesNumberText()
            ShareIcon()
            CastIcon()
            StarIcon()
            StarsNumberText()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            SoundWaveImage()
            AlbumArtImage()
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.wrapContentSize()) {
            PreviousIcon()
            RepeatIcon()
            PlayIcon()
            ShuffleIcon()
            NextIcon()
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.now_playing_mock_time),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        TipIcon()
    }
}

@Composable
private fun SongName() {
    Text(
        text = stringResource(id = R.string.now_playing_mock_song_name),
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun ArtistName() {
    Text(
        text = stringResource(id = R.string.now_playing_mock_artist_name),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.secondary
    )
}

@Composable
private fun SharesNumberText() {
    Text(
        modifier = Modifier.offset(x = (-34).dp, y = 28.dp),
        text = stringResource(id = R.string.now_playing_mock_number_shares),
        fontSize = 10.sp,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun ArtistBackgroundImage() {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        painter = painterResource(R.drawable.now_playing_bg_masked_artist),
        contentDescription = "Artist Image",
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun ShareIcon() {
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(x = (-28).dp, y = 14.dp),
        painter = painterResource(R.drawable.now_playing_ic_share),
        contentDescription = "Share"
    )
}

@Composable
private fun CastIcon() {
    Image(
        modifier = Modifier.wrapContentSize(),
        painter = painterResource(R.drawable.now_playing_ic_cast),
        contentDescription = "Cast"
    )
}

@Composable
private fun StarIcon() {
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(x = 28.dp, y = 14.dp),
        painter = painterResource(R.drawable.now_playing_ic_star),
        contentDescription = "Star Song"
    )
}

@Composable
private fun StarsNumberText() {
    Text(
        modifier = Modifier.offset(x = 34.dp, y = 28.dp),
        text = stringResource(id = R.string.now_playing_mock_number_stars),
        style = MaterialTheme.typography.body2,
        fontSize = 10.sp,
        color = MaterialTheme.colors.primary
    )
}

@Composable
private fun SoundWaveImage() {
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(R.drawable.now_playing_bg_sound_wave),
        contentDescription = "Wave",
        contentScale = ContentScale.FillHeight
    )
}

@Composable
private fun AlbumArtImage() {
    Image(
        modifier = Modifier.wrapContentSize(),
        painter = painterResource(R.drawable.now_playing_bg_album_art),
        contentDescription = "Play"
    )
}

@Composable
private fun PreviousIcon() {
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(x = (-34).dp, y = (-52).dp),
        painter = painterResource(R.drawable.now_playing_ic_previous),
        contentDescription = "Previous Song"
    )
}

@Composable
private fun RepeatIcon() {
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(x = (-28).dp, y = (-14).dp),
        painter = painterResource(R.drawable.now_playing_ic_repeat),
        contentDescription = "Repeat"
    )
}

@Composable
private fun PlayIcon() {
    Image(
        modifier = Modifier.wrapContentSize(),
        painter = painterResource(R.drawable.now_playing_ic_play),
        contentDescription = "Play"
    )
}

@Composable
private fun ShuffleIcon() {
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(x = 28.dp, y = (-14).dp),
        painter = painterResource(R.drawable.now_playing_ic_shuffle),
        contentDescription = "Shuffle"
    )
}

@Composable
private fun NextIcon() {
    Image(
        modifier = Modifier
            .wrapContentSize()
            .offset(x = 34.dp, y = (-52).dp),
        painter = painterResource(R.drawable.now_playing_ic_next),
        contentDescription = "Next Song"
    )
}

@Composable
private fun ColumnScope.TipIcon() {
    Image(
        modifier = Modifier.wrapContentSize(),
        painter = painterResource(R.drawable.now_playing_ic_tipping),
        contentDescription = "Tip"
    )
}