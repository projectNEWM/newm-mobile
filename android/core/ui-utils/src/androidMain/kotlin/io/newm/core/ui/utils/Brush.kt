package io.newm.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import io.newm.core.resources.R


@Composable
fun HotPinkBrush(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.purple_artist_ring_1),
            colorResource(id = R.color.gradient_purple),
            colorResource(id = R.color.purple_artist_ring_1)
        )
    )
}

@Composable
fun DisabledHotPinkBrush(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.purple_artist_ring_1),
            colorResource(id = R.color.purple_artist_ring_1),
            colorResource(id = R.color.purple_artist_ring_1)
        )
    )
}

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
fun ActionButtonBackgroundBrush(): Brush {
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
