package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.CloudinaryManager
import io.newm.shared.commonInternal.repositories.UserRepository
import io.newm.shared.commonPublic.usecases.UpdateProfilePictureUseCase

internal class UpdateProfilePictureUseCaseImpl(
    private val cloudinaryManager: CloudinaryManager,
    private val userRepository: UserRepository
) : UpdateProfilePictureUseCase {
    override suspend fun updateProfilePicture(filePath: String) {
        val url = cloudinaryManager.uploadImage(
            filePath = filePath,
            options = mapOf("eager" to "c_lfill,w_400,h_400")
        )
        userRepository.updateUserPicture(url)
    }

    override suspend fun removeProfilePicture() {
        userRepository.updateUserPicture(null)
    }
}