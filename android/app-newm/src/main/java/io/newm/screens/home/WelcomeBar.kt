package io.newm.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R

@Composable
fun WelcomeBar(
    name: String,
    avatarUrl: String,
    onAvatarClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(120.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = avatarUrl,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .clickable { onAvatarClick() },
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .padding(start = 12.dp),
            text = stringResource(id = R.string.welcome, name),
            style = MaterialTheme.typography.h4,
            fontSize = 18.sp,
        )
    }
}
