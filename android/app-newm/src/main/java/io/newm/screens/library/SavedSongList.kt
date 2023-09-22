package io.newm.screens.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.core.theme.Gray100
import io.newm.core.theme.White
import io.newm.core.theme.inter

data class SongModel(
    val title: String,
    val artist: String,
    val imageUrl: String,
)

@Composable
fun SavedSongList(
    songModels: List<SongModel>,
    onSongView: (SongModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 4.dp),
            text = stringResource(id = R.string.title_saved_song_list),
            fontFamily = inter,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Gray100
        )
        if (songModels.isEmpty()) {
            EmptyStateItem()
        } else {
            songModels.forEach { song ->
                SavedSongItem(song) {
                    onSongView(song)
                }
            }
        }
    }
}


@Composable
private fun SavedSongItem(
    songModel: SongModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = songModel.imageUrl,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = songModel.title,
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = White
            )
            Text(
                text = songModel.artist,
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray100
            )
        }
    }
}
