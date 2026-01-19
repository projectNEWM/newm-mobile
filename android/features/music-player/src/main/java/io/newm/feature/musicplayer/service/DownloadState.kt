package io.newm.feature.musicplayer.service

sealed class DownloadState {
    data object None : DownloadState()

    data class Downloading(
        val progress: Float,
    ) : DownloadState()

    data class Failed(
        val error: String,
    ) : DownloadState()

    data object Completed : DownloadState()
}
