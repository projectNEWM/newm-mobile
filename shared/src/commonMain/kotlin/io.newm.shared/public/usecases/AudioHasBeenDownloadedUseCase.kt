package io.newm.shared.public.usecases

import io.newm.shared.public.models.NFTTrack
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AudioHasBeenDownloadedUseCase {
    fun fileExistsForTrack(track: NFTTrack): Boolean
}

class AudioHasBeenDownloadedUseCaseProvider(): KoinComponent {
    private val audioHasBeenDownloadedUseCase: AudioHasBeenDownloadedUseCase by inject()

    fun get(): AudioHasBeenDownloadedUseCase {
        return this.audioHasBeenDownloadedUseCase
    }
}