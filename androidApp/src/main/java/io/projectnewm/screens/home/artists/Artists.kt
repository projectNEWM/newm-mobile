package io.projectnewm.screens.home.artists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.projectnewm.R
import io.projectnewm.components.ArtistBackgroundBrush
import io.projectnewm.components.ArtistRingBrush
import io.projectnewm.components.RingDecorator
import io.projectnewm.components.SongRingBrush

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistList() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center

    ) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "NEWM Artists",
                style = MaterialTheme.typography.h6
            )

            LazyRow {
                repeat((0..25).count()) {
                    item {
                        ArtistCard({})
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistCard(onArtistClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(160.dp)
            .padding(8.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = { onArtistClicked }
    ) {
        Column(
            modifier = Modifier.background(brush = ArtistBackgroundBrush()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val modifier = Modifier
                .height(100.dp)
                .width(100.dp)
            RingDecorator(modifier = modifier, brush = ArtistRingBrush()) {
                Image(
                    modifier = modifier
                        .clip(CircleShape)
                        .padding(all = 5.dp),
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Artist",
                )
            }
            Text(text = "Artist Name")
            Text(text = "Genre", color = colorResource(id = R.color.purple_500))
            Text(text = "12K")
        }
    }
}