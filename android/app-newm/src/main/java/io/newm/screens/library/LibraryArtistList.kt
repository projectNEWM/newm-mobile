package io.newm.screens.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import io.newm.core.theme.Gray100
import io.newm.core.theme.White
import io.newm.core.theme.inter

data class LibraryArtistModel(
    val name: String,
    val songCount: Int,
    val imageUrl: String,
)

@Composable
fun LibraryArtistList(
    title: String,
    artistModels: List<LibraryArtistModel>,
    onViewDetails: (LibraryArtistModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
    ) {
        Row() {
            Text(
                text = title,
                fontFamily = inter,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Gray100
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (artistModels.isEmpty()) {
                EmptyStateItem()
            } else {
                artistModels.forEach { artist ->
                    ArtistItem(artist) {
                        onViewDetails(artist)
                    }
                }
            }
        }
    }
}

@Composable
private fun ArtistItem(
    artist: LibraryArtistModel,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = artist.imageUrl,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .clickable { onClick() },
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column {
            Text(
                text = artist.name,
                modifier = Modifier.padding(start = 12.dp),
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = White
            )
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                text = stringResource(id = R.string.artist_list_songs, artist.songCount),
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray100
            )
        }
    }
}
