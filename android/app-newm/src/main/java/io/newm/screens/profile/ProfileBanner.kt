package io.newm.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileBanner(
    modifier: Modifier = Modifier,
    bannerUrl: String,
    avatarUrl: String,
) {
    Box(
        modifier = modifier
            .height(230.dp)
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = bannerUrl,
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            placeholder = gradient(),
            contentDescription = null,
        )
        AsyncImage(
            model = avatarUrl,
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape),
            placeholder = gradient(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }
}

@Composable
private fun gradient() = BrushPainter(
    Brush.linearGradient(
        listOf(
            Color.White,
            Color.LightGray,
        )
    )
)
