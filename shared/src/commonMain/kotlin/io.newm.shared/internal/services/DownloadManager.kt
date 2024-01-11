package io.newm.shared.internal.services

import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import shared.fileURLForDownloadUrl
import shared.saveFileToDisk

internal class DownloadManager {
    private val httpClient = HttpClient()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend fun download(url: String, progressCallback: (Double) -> Unit): String = withContext(scope.coroutineContext) {
        val data = httpClient.get(url) {
            onDownload { bytesSentTotal, contentLength ->
                val progress = if (contentLength != null) {
                    bytesSentTotal.toDouble() / contentLength.toDouble()
                } else {
                    -1.0 // Indicates progress is unknown
                }
                withContext(Dispatchers.Main) {
                    progressCallback(progress)
                }
            }
        }.readBytes()
        return@withContext saveFileToDisk(data, fileURLForDownloadUrl(url))
    }

    fun shutdown() {
        scope.cancel()
    }
}

// Usage example (from another coroutine context):
// val downloadManager = DownloadManager()
// try {
//     val filePath = downloadManager.download("https://example.com/file", "downloadedFile.txt") { progress ->
//         println("Download progress: $progress")
//     }
//     println("File saved to: $filePath")
// } catch (e: Exception) {
//     // Handle error
// }
