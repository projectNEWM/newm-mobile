package io.newm.shared.internal.repositories

import io.newm.shared.internal.services.DownloadManager
import shared.clearFiles
import shared.fileExists
import shared.fileURLForDownloadUrl

class FileRepository {
    private val downloadManager = DownloadManager()

    suspend fun getFile(downloadURL: String, progress: (Double) -> Unit): String {
        if (fileExists(downloadURL)) {
            return fileURLForDownloadUrl(downloadURL)
        } else {
            progress(0.0)
            return downloadManager.download(url = downloadURL, progressCallback = progress)
        }
    }

    fun deleteAllFiles() {
        clearFiles()
    }
}
