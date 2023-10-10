package io.newm.feature.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MusicPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val songId = requireNotNull(intent.getStringExtra(EXTRA_SONG_ID))
        setContent {
            MusicPlayerScreen(songId, onNavigateUp = { finish() })
        }
    }

    companion object {
        private const val EXTRA_SONG_ID = "songId"
    }
}