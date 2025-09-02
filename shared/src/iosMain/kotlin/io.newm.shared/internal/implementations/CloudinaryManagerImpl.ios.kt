package io.newm.shared.internal.implementations

import io.newm.shared.commonInternal.CloudinaryManager
import io.newm.shared.commonInternal.api.NewmCloudinaryAPI


internal class CloudinaryManagerImpl(
    private val newmCloudinaryAPI: NewmCloudinaryAPI
) : CloudinaryManager {

    override suspend fun uploadImage(filePath: String, options: Map<String, Any>): String {
        // TODO: Implement using iOS Cloudinary SDK
        return ""
    }
}
