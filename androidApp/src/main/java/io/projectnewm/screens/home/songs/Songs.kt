package io.projectnewm.screens.home.songs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.projectnewm.R
import io.projectnewm.components.RingDecorator
import io.projectnewm.components.SongRingBrush

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewmSongList() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(8.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "NEWM Songs",
                style = MaterialTheme.typography.h6
            )
            LazyRow {
                repeat((0..25).count()) {
                    item {
                        SongCard({ })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongCard(onSongClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(150.dp)
            .padding(8.dp),
        elevation = 2.dp,
        onClick = { onSongClicked }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val modifier = Modifier
                .height(100.dp)
                .width(100.dp)
            RingDecorator(modifier = modifier, brush = SongRingBrush()) {
                Image(
                    modifier = modifier
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_song_icon),
                    contentDescription = "Artist",
                )
            }
            Text(text = "Song Title")
            Text(text = "Artist /  Band")
        }
    }
}
