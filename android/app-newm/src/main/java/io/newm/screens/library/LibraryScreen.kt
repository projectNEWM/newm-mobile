package io.newm.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.core.theme.CerisePink
import io.newm.core.theme.DarkPink
import io.newm.core.theme.DarkViolet
import io.newm.core.theme.Gray16
import io.newm.core.theme.GraySuit
import io.newm.core.theme.SteelPink
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.theme.raleway
import io.newm.core.ui.text.SearchBar
import io.newm.core.ui.utils.drawWithBrush
import io.newm.core.ui.utils.secondsToMinutesSecondsString
import io.newm.core.ui.utils.textGradient

internal const val TAG_LIBRARY_SCREEN = "TAG_LIBRARY_SCREEN"
internal val LibraryBrush = Brush.horizontalGradient(listOf(DarkViolet, DarkPink))

@Composable
fun LibraryScreen(
    onSongPlay: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_LIBRARY_SCREEN)
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState())
    ) {

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 19.dp),
            text = stringResource(id = R.string.title_library),
            style = TextStyle(
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                brush = textGradient(SteelPink, CerisePink)
            )
        )
        if (savedSongModels.isEmpty()) {
            EmptyLibraryContent(modifier = Modifier.weight(1f))
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    placeholderResId = R.string.library_search,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onQueryChange = { /*TODO*/ }
                )

                IconButton(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 16.dp),
                    onClick = {/*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_library_filter),
                        contentDescription = "Filter",
                        modifier = Modifier
                            .drawWithBrush(LibraryBrush)

                    )
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Gray16)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    for (model in savedSongModels) {
                        LibraryItem(songModel = model) { onSongPlay(model.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun LibraryItem(
    songModel: SongModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = songModel.imageUrl,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Text(
                text = songModel.title,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = White
            )
            Text(
                text = songModel.artist,
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = GraySuit
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = songModel.duration.secondsToMinutesSecondsString(),
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = GraySuit
        )
    }
}
