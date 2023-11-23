package io.newm.feature.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import io.newm.feature.musicplayer.viewmodel.MusicPlayerViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MusicPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val songId = requireNotNull(intent.getStringExtra(EXTRA_SONG_ID))

        setContent {
            val mediaPlayer = rememberMediaPlayer()

            val viewModel by produceState<MusicPlayerViewModel?>(initialValue = null, mediaPlayer) {
                mediaPlayer?.let { player ->
                    value = getViewModel {
                        parametersOf(
                            songId,
                            player
                        )
                    }
                }
            }

            viewModel ?: return@setContent // TODO show loading screen

            MusicPlayerScreen(onNavigateUp = { finish() }, viewModel!!)
        }
    }

    companion object {
        private const val EXTRA_SONG_ID = "songId"
    }
}


