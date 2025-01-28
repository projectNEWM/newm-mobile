package io.newm.feature.musicplayer.service

import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.scheduler.Requirements
import io.newm.shared.NewmAppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

interface DownloadStateManager {
    fun getDownloadState(id: String): Flow<DownloadState>
}

@UnstableApi
class DownloadStateManagerImpl(
    private val exoDownloadManager: DownloadManager,
    private val scope: CoroutineScope,
    private val logger: NewmAppLogger,
): DownloadStateManager {
    private val downloadStates = ConcurrentHashMap<String, MutableStateFlow<DownloadState>>()

    init {
        initializeDownloadStates()

        exoDownloadManager.addListener(
            object : DownloadManager.Listener {
                override fun onDownloadsPausedChanged(
                    downloadManager: DownloadManager,
                    downloadsPaused: Boolean
                ) {
                    super.onDownloadsPausedChanged(downloadManager, downloadsPaused)
                    println("Downloads paused: $downloadsPaused")
                }

                override fun onDownloadChanged(
                    downloadManager: DownloadManager,
                    download: Download,
                    finalException: Exception?
                ) {
                    super.onDownloadChanged(downloadManager, download, finalException)

                    when {
                        finalException != null -> {
                            updateDownloadState(
                                download.request.id,
                                DownloadState.Failed(finalException.message ?: "Download failed")
                            )
                        }
                        download.state == Download.STATE_COMPLETED -> {
                            updateDownloadState(
                                download.request.id,
                                DownloadState.Completed
                            )
                        }
                        download.state == Download.STATE_DOWNLOADING -> {
                            updateDownloadState(
                                download.request.id,
                                DownloadState.Downloading(download.percentDownloaded / 100f)
                            )
                        }
                    }
                }

                override fun onDownloadRemoved(
                    downloadManager: DownloadManager,
                    download: Download
                ) {
                    super.onDownloadRemoved(downloadManager, download)
                    println("Download removed: $download")
                }

                override fun onIdle(downloadManager: DownloadManager) {
                    super.onIdle(downloadManager)
                    println("DownloadManager idle")
                }

                override fun onRequirementsStateChanged(
                    downloadManager: DownloadManager,
                    requirements: Requirements,
                    notMetRequirements: Int
                ) {
                    super.onRequirementsStateChanged(
                        downloadManager,
                        requirements,
                        notMetRequirements
                    )
                    println("Requirements state changed: $requirements")
                }

                override fun onWaitingForRequirementsChanged(
                    downloadManager: DownloadManager,
                    waitingForRequirements: Boolean
                ) {
                    super.onWaitingForRequirementsChanged(downloadManager, waitingForRequirements)
                    println("Waiting for requirements: $waitingForRequirements")
                }
            }
        )
    }

    private fun initializeDownloadStates() {
        scope.launch(Dispatchers.IO) {
            try {
                exoDownloadManager.downloadIndex.getDownloads().use { cursor ->
                    while (cursor.moveToNext()) {
                        val download = cursor.download
                        when (download.state) {
                            Download.STATE_COMPLETED -> {
                                updateDownloadState(
                                    download.request.id,
                                    DownloadState.Completed
                                )
                            }

                            Download.STATE_DOWNLOADING -> {
                                updateDownloadState(
                                    download.request.id,
                                    DownloadState.Downloading(download.percentDownloaded / 100f)
                                )
                            }

                            Download.STATE_FAILED -> {
                                updateDownloadState(
                                    download.request.id,
                                    DownloadState.Failed("Download failed")
                                )
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                logger.error("DownloadManager","Failed to initialize download states", e)
            }
        }

    }

    override fun getDownloadState(id: String): Flow<DownloadState> {
        return downloadStates.getOrPut(id) { MutableStateFlow(DownloadState.None) }
    }

    fun updateDownloadState(id: String, state: DownloadState) {
        downloadStates.getOrPut(id) { MutableStateFlow(DownloadState.None) }
            .value = state
    }
}