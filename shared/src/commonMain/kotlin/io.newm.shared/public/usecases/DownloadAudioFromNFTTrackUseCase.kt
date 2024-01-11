package io.newm.shared.public.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface DownloadAudioFromNFTTrackUseCase {
    /// Download the audio from an NFT track and return the local URL to the file.
    suspend fun downloadAudioFromNFTTrack(nftTrackId: String, progress: (Double) -> Unit): String
}

class DownloadAudioFromNFTTrackUseCaseProvider(): KoinComponent {
    private val downloadAudioFromNFTTrackUseCase: DownloadAudioFromNFTTrackUseCase by inject()

    fun get(): DownloadAudioFromNFTTrackUseCase {
        return this.downloadAudioFromNFTTrackUseCase
    }
}