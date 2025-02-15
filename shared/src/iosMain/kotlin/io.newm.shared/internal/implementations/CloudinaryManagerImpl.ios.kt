package io.newm.shared.internal.implementations

import io.newm.shared.internal.CloudinaryManager
import io.newm.shared.internal.api.NewmCloudinaryAPI


internal class CloudinaryManagerImpl(
    private val newmCloudinaryAPI: NewmCloudinaryAPI
) : CloudinaryManager {

    override suspend fun uploadImage(filePath: String, options: Map<String, Any>): String {
        // TODO: Implement using iOS Cloudinary SDK
        return ""
    }
}
