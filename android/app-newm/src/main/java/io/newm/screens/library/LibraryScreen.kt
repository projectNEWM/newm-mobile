package io.newm.screens.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.R
import io.newm.core.theme.*
import io.newm.core.ui.utils.textGradient

internal const val TAG_LIBRARY_SCREEN = "TAG_LIBRARY_SCREEN"


@OptIn(ExperimentalTextApi::class)
@Composable
fun LibraryScreen(
    onSongView: (SongModel) -> Unit,
    onArtistViewDetails: (LibraryArtistModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(state = rememberScrollState())
            .testTag(TAG_LIBRARY_SCREEN)
    ) {
        Text(
            text = stringResource(id = R.string.title_library),
            style = TextStyle(
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                brush = textGradient(SteelPink, CerisePink)
            )
        )
        SavedSongList(savedSongModels, onSongView)
        LibraryArtistList(
            title = stringResource(id = R.string.library_artists),
            artistModels = libraryArtistListModels,
            onViewDetails = onArtistViewDetails
        )
    }
}
