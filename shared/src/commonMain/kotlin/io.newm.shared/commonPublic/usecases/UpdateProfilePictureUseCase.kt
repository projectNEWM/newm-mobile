package io.newm.shared.commonPublic.usecases

import io.newm.shared.commonPublic.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException

interface UpdateProfilePictureUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun updateProfilePicture(filePath: String)

    @Throws(KMMException::class, CancellationException::class)
    suspend fun removeProfilePicture()
}
