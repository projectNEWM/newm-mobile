package io.newm.screens.library

import android.util.Log
import androidx.compose.foundation.clickable
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
import io.newm.core.ui.text.SearchBar
import io.newm.core.ui.utils.textGradient
import io.newm.shared.models.Song
import org.koin.compose.koinInject

internal const val TAG_NFTLIBRARY_SCREEN = "TAG_LIBRARY_SCREEN"


@Composable
fun NFTLibraryScreen(
    onPlaySong: (Song) -> Unit,
    onConnectWalletClick: () -> Unit,
    viewModel: NFTLibraryViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()
    viewModel.setXPub("xpub6")
    when (state) {
        NFTLibraryState.Loading -> LoadingScreen()
        is NFTLibraryState.Content -> {
            SongList(songs = (state as NFTLibraryState.Content).songs, onPlaySong, onConnectWalletClick)
        }
    }

}

@OptIn(ExperimentalTextApi::class)
@Composable
fun SongList(songs: List<Song>, onPlaySong: (Song) -> Unit, onConnectWalletClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState())
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
        if (savedSongModels.isNotEmpty() ||
            savedAlbumsModels.isNotEmpty() ||
            libraryArtistListModels.isNotEmpty()
        ) {
            SearchBar(
                placeholderResId = R.string.library_search,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            )
        }

        PrimaryButton(text = "Connect Wallet", onClick = {
            Log.d("NFTLibraryScreen", "Login")
            onConnectWalletClick.invoke()
        })
        if (songs.isNotEmpty()) {
            songs.forEach { song ->
                RowSongItem(song = song, onClick = onPlaySong)
            }
        }


    }
}

@Composable
private fun RowSongItem(
    song: Song,
    onClick: (Song) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable(onClick = { onClick(song) })
    ) {
        AsyncImage(
            model = song.coverArtUrl,
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
                text = song.title,
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = White
            )
            Text(
                text = song.ownerId,
                fontFamily = inter,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray100
            )
        }
    }
}



