package io.newm.feature.now.playing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.newm.core.ui.LoadingScreen
import org.koin.compose.koinInject

@Composable
fun DemoPlayerScreen(songId: String, viewModel: DemoPlayerViewModel = koinInject()) {
    viewModel.setSongId(songId)
    val state by viewModel.state.collectAsState()

    when(state) {
        is MusicPlayerState.Loading -> LoadingScreen()
        is MusicPlayerState.Content -> AudioPlayerViewer((state as MusicPlayerState.Content).song)
    }
}