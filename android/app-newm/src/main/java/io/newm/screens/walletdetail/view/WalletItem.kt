package io.newm.screens.walletdetail.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import io.newm.shared.commonPublic.models.NFTTrack
import io.newm.sharedfeatures.core.resources.R

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun WalletItem(track: NFTTrack) {
    val context = LocalContext.current
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        icon = {
            AsyncImage(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(4.dp)),
                model =
                    ImageRequest
                        .Builder(context)
                        .data(track.imageUrl)
                        .error(R.drawable.ic_default_track_cover_art)
                        .placeholder(R.drawable.ic_default_track_cover_art)
                        .build(),
                contentScale = ContentScale.Crop,
                contentDescription = track.title,
            )
        },
        text = { Text(text = track.title) },
        secondaryText = { Text(text = track.artists.joinToString()) },
    )
}
