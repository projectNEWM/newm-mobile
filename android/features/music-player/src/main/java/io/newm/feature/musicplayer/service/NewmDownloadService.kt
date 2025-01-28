package io.newm.feature.musicplayer.service

import android.app.Notification
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.Scheduler
import androidx.media3.exoplayer.workmanager.WorkManagerScheduler
import io.newm.feature.musicplayer.R
import org.koin.android.ext.android.inject

private const val WORK_NAME: String = "NewmDownload"
private const val FOREGROUND_NOTIFICATION_ID: Int = 1
private const val NOTIFICATION_UPDATE_INTERVAL: Long = 1_000
const val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel"

@UnstableApi
internal class NewmDownloadService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    NOTIFICATION_UPDATE_INTERVAL,
    DOWNLOAD_NOTIFICATION_CHANNEL_ID,
    R.string.musicplayer_exo_download_notification_channel_name,
    0
) {

    private val exoDownloadManager : DownloadManager by inject()

    override fun getDownloadManager(): DownloadManager {
        return exoDownloadManager
    }

    override fun getScheduler(): Scheduler {
        return WorkManagerScheduler(this, WORK_NAME)
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int
    ): Notification {
        val downloadNotificationHelper = DownloadNotificationHelper(
            this,
            DOWNLOAD_NOTIFICATION_CHANNEL_ID
        )

        return downloadNotificationHelper.buildProgressNotification(
            this,
            R.drawable.musicplayer_ic_download,
            null,
            null,
            downloads,
            notMetRequirements
        )
    }
}