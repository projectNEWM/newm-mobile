package io.newm.feature.musicplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalInspectionMode
import io.newm.feature.musicplayer.service.MediaSessionConnection
import io.newm.feature.musicplayer.service.MusicPlayer
import org.koin.compose.koinInject

/**
 * Observes the singleton [MusicPlayer] instance managed by [MediaSessionConnection]. This maintains
 * the connection across navigation.
 */
@Composable
fun observeMusicPlayer(): MusicPlayer? {
    if (LocalInspectionMode.current) return null

    val connection = koinInject<MediaSessionConnection>()
    val musicPlayer by connection.musicPlayer.collectAsState()

    return musicPlayer
}
