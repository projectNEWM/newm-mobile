package io.newm.feature.musicplayer.service

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import io.newm.shared.commonPublic.analytics.NewmAppEventLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MediaSessionConnection(
    context: Context,
    private val scope: CoroutineScope,
    private val eventLogger: NewmAppEventLogger,
) {
    private val _musicPlayer = MutableStateFlow<MusicPlayer?>(null)
    val musicPlayer = _musicPlayer.asStateFlow()

    init {
        val sessionToken = SessionToken(context, ComponentName(context, MediaService::class.java))
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()

        controllerFuture.addListener(
            {
                try {
                    val player = controllerFuture.get()
                    // MusicPlayerImpl interacts with MediaController which requires Main thread.
                    // We create a new scope based on the application scope but confined to Main
                    // dispatcher.
                    _musicPlayer.value = MusicPlayerImpl(player, scope, eventLogger)
                } catch (e: Exception) {
                    eventLogger.logEvent(
                        "MediaSessionConnectionError",
                        mapOf("message" to e.message),
                    )
                }
            },
            MoreExecutors.directExecutor(),
        )
    }
}
