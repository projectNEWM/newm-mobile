package io.newm.screens.home.songs

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewmSongList(onSongClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "NEWM Songs",
                style = MaterialTheme.typography.body1
            )
            LazyRow {
                repeat((0..25).count()) {
                    item {
                        SongCard(onSongClicked)
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
            .width(115.dp)
            .height(140.dp),
        elevation = 2.dp,
        onClick = onSongClicked,
        backgroundColor = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.offset(y = (-8).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    painter = painterResource(id = R.drawable.ic_newm_eclipse_orange_pink),
                    contentDescription = "Song",
                )
                Image(
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_bowie_artist),
                    contentDescription = "Song",
                )
            }
            Text(
                modifier = Modifier.offset(y = (-20).dp),
                text = "Song Title",
                fontSize = 11.sp
            )
            Text(
                modifier = Modifier.offset(y = (-20).dp),
                text = "Artist / Band",
                fontSize = 10.sp,
                color = colorResource(id = R.color.gradient_orange)
            )
        }
    }
}
