package io.newm.screens.home.curatedplaylists

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CuratedPlayLists() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        contentAlignment = Alignment.Center

    ) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Curated Playlists",
                style = MaterialTheme.typography.body1
            )

            LazyRow {
                repeat((0..25).count()) {
                    item {
                        PlaylistItem { }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistItem(onPlaylistClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(140.dp)
            .padding(8.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(100.dp),
        onClick = onPlaylistClicked,
        backgroundColor = Color.Black
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth(),
            painter = painterResource(R.drawable.mock_currated_playlist_item),
            contentDescription = "Newm Playlists",
        )
    }
}