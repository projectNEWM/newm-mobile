package io.newm.feature.musicplayer.service

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class MediaService : MediaSessionService() {
    private lateinit var mediaSession: MediaSession
    private lateinit var player : ExoPlayer

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player)
            .setCallback(MediaSessionCallback()).build()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
        player.release()
    }

    private inner class MediaSessionCallback : MediaSession.Callback {
        override fun onConnect(
            session: MediaSession,
            controller: MediaSession.ControllerInfo
        ): MediaSession.ConnectionResult {
            val connectionResult = super.onConnect(session, controller)
            val sessionCommands = connectionResult.availableSessionCommands

            return MediaSession.ConnectionResult.accept(
                sessionCommands, connectionResult.availablePlayerCommands
            )
        }
    }
}
