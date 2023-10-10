package io.newm.feature.musicplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.newm.core.ui.LoadingScreen
import org.koin.compose.koinInject

@Composable
fun MusicPlayerScreen(
    songId: String,
    onNavigateUp: () -> Unit,
    viewModel: MusicPlayerViewModel = koinInject()
) {
    viewModel.setSongId(songId)
    val state by viewModel.state.collectAsState()

    when (state) {
        is MusicPlayerState.Loading -> LoadingScreen()
        is MusicPlayerState.Content -> MusicPlayerViewer(
            song = (state as MusicPlayerState.Content).song,
            onNavigateUp = onNavigateUp
        )
    }
}