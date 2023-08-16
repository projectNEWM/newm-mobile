package io.newm.screens.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.core.theme.*

data class AlbumModel(
    val title: String,
    val genre: String,
    val imageUrl: String,
)

@Composable
fun MusicCarousel(
    title: String,
    albumModels: List<AlbumModel>,
    onViewDetails: (AlbumModel) -> Unit,
    onViewAll: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = title,
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Gray100
            )
            if (onViewAll != null) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.view_all),
                    modifier = Modifier.clickable { onViewAll() },
                    fontFamily = inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Purple
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 14.dp)
        ) {
            items(albumModels) { AlbumCarouselItem(it) { onViewDetails(it) } }
        }
    }
}

@Composable
private fun AlbumCarouselItem(
    albumModel: AlbumModel,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                model = albumModel.imageUrl,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }
        Text(
            text = albumModel.title,
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = inter,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = White
        )
        Text(
            text = albumModel.genre,
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Gray100
        )
    }
}
