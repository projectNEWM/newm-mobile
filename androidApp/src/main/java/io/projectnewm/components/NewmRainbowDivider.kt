package io.projectnewm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import io.projectnewm.R

@Composable
fun NewmRainbowDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = NewmRainbowBrush()
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
fun RingDecorator(
    modifier: Modifier = Modifier,
    brush: Brush,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .clip(CircleShape)
            .background(brush),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .padding(3.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}