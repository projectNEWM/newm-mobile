package io.newm.feature.musicplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.newm.core.ui.LoadingScreen
import io.newm.feature.musicplayer.viewmodel.MusicPlayerState
import io.newm.feature.musicplayer.viewmodel.MusicPlayerViewModel
import org.koin.compose.koinInject

@Composable
fun MusicPlayerScreen(
    onNavigateUp: () -> Unit,
    viewModel: MusicPlayerViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()

    when (val uiState = state) {
        is MusicPlayerState.Loading -> LoadingScreen()
        is MusicPlayerState.Content -> MusicPlayerViewer(
            song = uiState.song,
            onNavigateUp = onNavigateUp,
            playbackStatus = uiState.playbackStatus,
            onEvent = viewModel::onEvent
        )
    }
}
