package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.FileRepository
import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.usecases.AudioHasBeenDownloadedUseCase
import shared.fileExists

class AudioHasBeenDownloadedUseCaseImpl(
    private val fileRepo: FileRepository
): AudioHasBeenDownloadedUseCase {
    override fun fileExistsForTrack(track: NFTTrack): Boolean {
        return fileExists(track.audioUrl)
    }
}