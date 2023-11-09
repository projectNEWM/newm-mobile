package io.newm.screens.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.newm.core.resources.R
import io.newm.core.theme.*
import io.newm.core.ui.LoadingScreen
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.utils.ErrorScreen
import io.newm.core.ui.utils.textGradient
import io.newm.shared.public.models.NFTTrack
import org.koin.compose.koinInject

internal const val TAG_NFTLIBRARY_SCREEN = "TAG_LIBRARY_SCREEN"

@OptIn(ExperimentalTextApi::class)
@Composable
fun NFTLibraryScreen(
    onPlaySong: (NFTTrack) -> Unit,
    goToProfile: () -> Unit,
    viewModel: NFTLibraryViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag(TAG_NFTLIBRARY_SCREEN)
    ) {
        Text(
            text = stringResource(id = R.string.title_nft_library),
            modifier = Modifier.padding(vertical = 16.dp),
            style = TextStyle(
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                brush = textGradient(SteelPink, CerisePink)
            )
        )
        when (state) {
            NFTLibraryState.Loading -> LoadingScreen()
            NFTLibraryState.NoWalletFound -> EmptyNFTListScreen(goToProfile)
            is NFTLibraryState.Error -> ErrorScreen((state as NFTLibraryState.Error).message)
            is NFTLibraryState.Content -> {
                SongList(
                    songs = (state as NFTLibraryState.Content).songs,
                    onPlaySong = onPlaySong
                )
            }

        }
    }

}

@Composable
fun EmptyNFTListScreen(goToProfile: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_NFTLIBRARY_SCREEN),
        contentAlignment = Alignment.Center // This centers the content both horizontally and vertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text =
                "Connect your Cardano wallet using the xpub key to play songs from your NFTs."
            )

            PrimaryButton(text = "Go to profile", onClick = {
                goToProfile.invoke()
            })
        }
    }
}

@Composable
fun SongList(songs: List<NFTTrack>, onPlaySong: (NFTTrack) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .testTag(TAG_NFTLIBRARY_SCREEN)
    ) {
//        TODO: Implement Search
//        SearchBar(
//            placeholderResId = R.string.library_search,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//        )
        if (songs.isNotEmpty()) {
            songs.forEach { song ->
                RowSongItem(song = song, onClick = onPlaySong)
            }
        }
    }
}

@Composable
private fun RowSongItem(
    song: NFTTrack,
    onClick: (NFTTrack) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable(onClick = { onClick(song) })
    ) {
        AsyncImage(
            model = song.imageUrl,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = song.name,
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = White
            )
            Text(
                text = song.artists.joinToString(separator = ", "),
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray100
            )
        }
    }
}



