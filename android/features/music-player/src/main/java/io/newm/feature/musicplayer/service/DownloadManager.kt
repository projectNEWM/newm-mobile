package io.newm.feature.musicplayer.service

import android.content.Context
import android.net.Uri
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import kotlinx.coroutines.flow.Flow

interface DownloadManager {
    fun download(id: String, url: String)
    fun getDownloadState(id: String): Flow<DownloadState>
}

@UnstableApi
class DownloadManagerImpl(
    private val context: Context,
    private val downloadStateManager: DownloadStateManager,
) : DownloadManager {

    @UnstableApi
    override fun download(id: String, url: String) {
        val uri = Uri.parse(url)
        val downloadRequest = DownloadRequest.Builder(id, uri).build()

        DownloadService.sendAddDownload(
            context,
            NewmDownloadService::class.java,
            downloadRequest,
            true
        )
    }

    override fun getDownloadState(id: String): Flow<DownloadState> {
        return downloadStateManager.getDownloadState(id)
    }
}