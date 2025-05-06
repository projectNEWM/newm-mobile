package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException

interface UpdateProfilePictureUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun updateProfilePicture(filePath: String)

    @Throws(KMMException::class, CancellationException::class)
    suspend fun removeProfilePicture()
}

