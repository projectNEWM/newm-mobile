package io.projectnewm.screens.home.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.projectnewm.core.resources.R
import io.projectnewm.core.ui.utils.ArtistBackgroundBrush

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistList() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        contentAlignment = Alignment.Center

    ) {
        Column {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "NEWM Artists",
                style = MaterialTheme.typography.body1
            )

            LazyRow {
                repeat((0..25).count()) {
                    item {
                        ArtistCard { }
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
            .width(130.dp)
            .height(185.dp)
            .padding(8.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = onArtistClicked
    ) {
        Column(
            modifier = Modifier.background(brush = ArtistBackgroundBrush()),
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
                    painter = painterResource(id = R.drawable.ic_newm_eclipse_purple_pink),
                    contentDescription = "Artist",
                )
                Image(
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_bowie_artist),
                    contentDescription = "Artist",
                )
            }
            Text(
                modifier = Modifier.offset(y = (-8).dp),
                text = "Artist Name",
                fontSize = 11.sp
            )
            Text(
                modifier = Modifier.offset(y = (-8).dp),
                text = "Genre",
                fontSize = 10.sp,
                color = colorResource(id = R.color.gradient_purple)
            )
            Text(
                modifier = Modifier.offset(y = (-8).dp),
                text = "12K",
                fontSize = 10.sp,
            )
        }
    }
}