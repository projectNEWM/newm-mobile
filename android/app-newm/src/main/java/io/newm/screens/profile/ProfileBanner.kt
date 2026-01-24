package io.newm.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import io.newm.core.resources.R
import io.newm.core.ui.theme.White50

@Composable
fun ProfileBanner(
    modifier: Modifier = Modifier,
    bannerUrl: String,
    avatarUrl: String,
    onAvatarClick: (() -> Unit)?,
) {
    Box(modifier = modifier.height(230.dp).fillMaxWidth()) {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(bannerUrl)
                    .error(R.drawable.ic_banner_placeholder)
                    .placeholder(R.drawable.ic_banner_placeholder)
                    .build(),
            modifier = Modifier.height(160.dp).fillMaxWidth(),
            contentScale = ContentScale.Crop,
            placeholder = gradient(),
            contentDescription = null,
        )
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(avatarUrl)
                    .error(R.drawable.ic_avatar_placeholder)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .build(),
            modifier =
                Modifier
                    .size(140.dp)
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .then(
                        if (onAvatarClick != null) {
                            Modifier.clickable(onClick = onAvatarClick)
                        } else {
                            Modifier
                        },
                    ),
            placeholder = painterResource(R.drawable.ic_default_moster),
            error = painterResource(R.drawable.ic_default_moster),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        if (onAvatarClick != null) {
            Icon(
                painter = painterResource(R.drawable.ic_add_circle),
                contentDescription = null,
                tint = White50,
                modifier =
                    Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                        .offset(x = 54.dp, y = 90.dp)
                        .background(Color.Transparent),
            )
        }
    }
}

@Composable
private fun gradient() = BrushPainter(Brush.linearGradient(listOf(Color.White, Color.LightGray)))
