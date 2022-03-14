package io.projectnewm.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.projectnewm.screens.home.artists.ArtistList
import io.projectnewm.screens.home.categories.CategoryTabs
import io.projectnewm.screens.home.categories.MusicalCategoriesViewModel
import io.projectnewm.screens.home.curatedplaylists.CuratedPlayLists
import io.projectnewm.screens.home.songs.NewmSongList

internal const val TAG_HOME_SCREEN = "TAG_HOME_SCREEN"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onClickSong: () -> Unit,
    viewModel: MusicalCategoriesViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TAG_HOME_SCREEN)
    ) {

        stickyHeader {
            val viewState by viewModel.state.collectAsState()
            val selectedCategory = viewState.selectedCategory

            if (viewState.categories.isNotEmpty() && selectedCategory != null) {
                CategoryTabs(
                    categories = viewState.categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = viewModel::onCategorySelected,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        item {
            ArtistList()
        }
        item {
            NewmSongList(onClickSong)
        }
        item {
            CuratedPlayLists()
        }
        item {
            NewAlbums()
        }
    }
}

@Composable
fun NewAlbums() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .border(border = BorderStroke(1.dp, color = Color.Black), shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "New Albums")
    }
}