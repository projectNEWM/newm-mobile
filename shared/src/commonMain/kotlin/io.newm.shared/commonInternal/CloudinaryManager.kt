package io.newm.shared.commonInternal

import io.newm.shared.commonPublic.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException

interface CloudinaryManager {

    /**
     * Uploads an image to Cloudinary.
     * @param filePath The path to the image file.
     * @param options The options to use when uploading the image.
     * @return The URL of the uploaded image.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun uploadImage(filePath: String, options: Map<String, Any>): String
}
