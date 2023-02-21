package io.newm.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R

internal const val TAG_HOME_SCREEN = "TAG_HOME_SCREEN"

@Composable
fun HomeScreen(
    onAvatarClick: () -> Unit,
    onSearchClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_HOME_SCREEN),
        verticalArrangement = Arrangement.Top
    ) {
        WelcomeBar(
            name = "Abel", //TODO: Replace Hardcoded values with values from ViewModel
            avatarUrl = "https://cdns-images.dzcdn.net/images/artist/033d460f704896c9caca89a1d753a137/200x200.jpg",
            onAvatarClick = onAvatarClick,
            onSearchClick = onSearchClick,
        )
    }
}

@Composable
private fun WelcomeBar(
    name: String,
    avatarUrl: String,
    onAvatarClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(120.dp)
            .padding(start = 20.dp, end = 20.dp)
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
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Image(
            modifier = Modifier
                .size(44.dp)
                .clickable { onSearchClick() },
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
        )
    }
}