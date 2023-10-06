package io.newm.feature.now.playing

import android.net.Uri

interface PlaybackController {
    fun playMedia(uri: Uri)
    fun pauseMedia()
}