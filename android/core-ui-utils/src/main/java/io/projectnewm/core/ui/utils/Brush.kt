package io.projectnewm.core.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import io.projectnewm.core.resources.R

@Composable
fun SongRingBrush(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.orange_song_ring_1),
            colorResource(id = R.color.orange_song_ring_2),
            colorResource(id = R.color.orange_song_ring_1),
            colorResource(id = R.color.orange_song_ring_2),
            colorResource(id = R.color.orange_song_ring_1),
        )
    )
}

@Composable
fun ArtistBackgroundBrush(): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.purple_artist_background_1),
            colorResource(id = R.color.purple_artist_background_2)
        )
    )
}

@Composable
fun NewmRainbowBrush(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.gradient_blue),
            colorResource(id = R.color.gradient_dark_blue),
            colorResource(id = R.color.gradient_purple),
            colorResource(id = R.color.gradient_pink),
            colorResource(id = R.color.gradient_red),
            colorResource(id = R.color.gradient_orange),
            colorResource(id = R.color.gradient_yellow),
            colorResource(id = R.color.gradient_green),
        )
    )
}
