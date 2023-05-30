package io.newm.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileBanner(
    bannerUrl: String,
    avatarUrl: String,
) {
    Box(
        modifier = Modifier
            .height(241.dp)
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = bannerUrl,
            modifier = Modifier
                .height(177.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        AsyncImage(
            model = avatarUrl,
            modifier = Modifier
                .size(128.dp)
                .offset(x = 20.dp, y = 113.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }
}