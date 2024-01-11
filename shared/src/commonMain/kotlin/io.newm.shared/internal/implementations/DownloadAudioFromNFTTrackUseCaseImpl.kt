package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.FileRepository
import io.newm.shared.public.usecases.DownloadAudioFromNFTTrackUseCase

internal class DownloadAudioFromNFTTrackUseCaseImpl : DownloadAudioFromNFTTrackUseCase {
    private val fileRepo = FileRepository()
    override suspend fun downloadAudioFromNFTTrack(
        nftTrackId: String,
        progress: (Double) -> Unit
    ): String {
        return fileRepo.getFile(nftTrackId, progress)
    }
}