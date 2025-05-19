package io.newm.shared.internal.implementations

import android.content.Context
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.signed.Signature
import com.cloudinary.android.signed.SignatureProvider
import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.CloudinaryManager
import io.newm.shared.internal.api.NewmCloudinaryAPI
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val TAG = "CloudinaryManagerImpl"

internal class CloudinaryManagerImpl(
    private val context: Context,
    private val newmCloudinaryAPI: NewmCloudinaryAPI,
    private val logger: NewmAppLogger
) : CloudinaryManager, SignatureProvider {

    init {
        MediaManager.init(context, this)
    }

    override suspend fun uploadImage(filePath: String, options: Map<String, Any>): String {
        return suspendCancellableCoroutine { continuation ->
            val requestId = MediaManager.get().upload(filePath)
                .options(options.toMutableMap())
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        logger.debug(TAG, "Upload $requestId started")
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        logger.debug(TAG, "Upload $requestId progress: ${1000 * bytes / totalBytes / 10.0}%")
                    }

                    override fun onSuccess(requestId: String, resultData: MutableMap<Any?, Any?>) {
                        logger.debug(TAG, "Upload $requestId done successfully: $resultData")
                        continuation.resume(resultData["secure_url"] as String)
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        val message = "${error.description} (code: ${error.code})"
                        logger.debug(TAG, "Upload $requestId failed: $message")
                        continuation.resumeWithException(KMMException("Cloudinary upload failed: $message"))
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                        logger.debug(TAG, "Upload $requestId rescheduled")
                    }
                })
                .startNow(context)

            continuation.invokeOnCancellation {
                MediaManager.get().cancelRequest(requestId)
                logger.debug(TAG, "Upload $requestId cancelled")
            }
        }
    }

    override fun provideSignature(options: MutableMap<Any?, Any?>): Signature {
        val response = runBlocking {
            @Suppress("UNCHECKED_CAST")
            newmCloudinaryAPI.sign(options as Map<String, Any>)
        }
        options["cloud_name"] = response.cloudName
        return Signature(response.signature, response.apiKey, response.timestamp)

    }

    override fun getName(): String = TAG
}
