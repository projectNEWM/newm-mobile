package io.newm.feature.musicplayer

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import io.newm.feature.musicplayer.service.MediaService
import io.newm.feature.musicplayer.service.MusicPlayerImpl
import io.newm.feature.musicplayer.viewmodel.MusicPlayerViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MusicPlayerActivity : ComponentActivity() {
    private val mediaPlayer: MutableState<Player?> = mutableStateOf(null)
    private var controllerFuture: ListenableFuture<MediaController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMediaController()

        val songId = requireNotNull(intent.getStringExtra(EXTRA_SONG_ID))

        setContent {
            val viewModel: MusicPlayerViewModel? =
                remember(mediaPlayer.value) {
                    mediaPlayer.value?.let { player ->
                        getViewModel {
                            parametersOf(
                                songId,
                                MusicPlayerImpl(player, lifecycleScope)
                            )
                        }
                    }
                }

            viewModel ?: return@setContent // TODO show loading screen

            MusicPlayerScreen(onNavigateUp = { finish() }, viewModel)
        }
    }

    private fun initMediaController() {
        val sessionToken = SessionToken(this, ComponentName(this, MediaService::class.java))
        controllerFuture = MediaController.Builder(this, sessionToken).buildAsync().apply {
            addListener(
                {
                    mediaPlayer.value = get()
                },
                MoreExecutors.directExecutor()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        controllerFuture?.let { future ->
            MediaController.releaseFuture(future)
        }
    }

    companion object {
        private const val EXTRA_SONG_ID = "songId"
    }
}
