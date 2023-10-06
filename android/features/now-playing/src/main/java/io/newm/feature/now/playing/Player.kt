package io.newm.feature.now.playing

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer


object Player {
    var player: ExoPlayer? = null

    @Synchronized
    fun play(context: Context) {
        if (player == null) {
            player = ExoPlayer.Builder(context).build()
//            Log.setLogLevel(LOG_LEVEL_OFF)
        }
        player?.apply {
            val mediaItem =
                MediaItem.fromUri("https://newm-test-audio.s3.us-west-2.amazonaws.com/CreativeCommonsDubstep.m3u8")
            addMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    @Synchronized
    fun pause() {
        player?.pause()
    }

    @Synchronized
    fun previous() {
        player?.seekTo(0)
    }
}


