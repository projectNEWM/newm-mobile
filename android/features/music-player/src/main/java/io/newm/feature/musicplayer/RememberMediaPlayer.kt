package io.newm.feature.musicplayer

import android.content.ComponentName
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import io.newm.feature.musicplayer.service.MediaService
import io.newm.feature.musicplayer.service.MusicPlayer
import io.newm.feature.musicplayer.service.MusicPlayerImpl

/**
 * Get a [MusicPlayer] instance that is scoped to the lifecycle of the current [LifecycleOwner].
 * This will be null if the [MediaService] is not running.
 * When the [LifecycleOwner] is destroyed, the [MusicPlayer] will be released.
 */
@Composable
fun rememberMediaPlayer(): MusicPlayer? {
    val mediaPlayer: MutableState<Player?> = remember { mutableStateOf(null) }

    val context = LocalContext.current
    val sessionToken =
        remember { SessionToken(context, ComponentName(context, MediaService::class.java)) }
    val controllerFuture = remember { MediaController.Builder(context, sessionToken).buildAsync() }

    LaunchedEffect(Unit) {
        controllerFuture.addListener(
            {
                mediaPlayer.value = controllerFuture.get()
            },
            MoreExecutors.directExecutor()
        )
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                MediaController.releaseFuture(controllerFuture)
                mediaPlayer.value = null
            }
        })
    }

    return mediaPlayer.value?.let { MusicPlayerImpl(it, lifecycleOwner.lifecycleScope) }
}
