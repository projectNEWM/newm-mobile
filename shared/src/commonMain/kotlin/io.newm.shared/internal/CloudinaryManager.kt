package io.newm.shared.internal

import io.newm.shared.public.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException

internal interface CloudinaryManager {

    /**
     * Uploads an image to Cloudinary.
     * @param filePath The path to the image file.
     * @param options The options to use when uploading the image.
     * @return The URL of the uploaded image.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun uploadImage(filePath: String, options: Map<String, Any>): String
}
