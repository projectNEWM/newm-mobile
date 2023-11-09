package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.public.models.Playlist
import io.newm.shared.public.models.Song
import io.newm.shared.internal.services.PlaylistAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class PlaylistRepository : KoinComponent {

    private val service: PlaylistAPI by inject()
    private val logger = Logger.withTag("NewmKMM-PlaylistRepository")

    suspend fun createPlaylist(name: String): String {
        logger.d { "createPlaylist" }
        return service.createPlaylist(name)
    }

    suspend fun updatePlaylistName(newPlaylistName: String, playlistId: String): Boolean {
        logger.d { "updatePlaylistName" }
        return service.updatePlaylistName(newPlaylistName, playlistId).status.value == 204
    }

    suspend fun getPlaylist(playlistId: String): Playlist {
        logger.d { "getPlaylist" }
        return service.getPlaylist(playlistId)
    }

    suspend fun getCurrentUserPlaylists(
        offset: Int?,
        limit: Int?,
        ids: String?,
        ownerIds: String?,
        olderThan: String?,
        newerThan: String?
    ): List<Playlist> {
        logger.d { "getCurrentUserPlaylists" }
        return service.getCurrentUserPlaylists(offset, limit, ids, ownerIds, olderThan, newerThan)
    }

    suspend fun getPlaylistCount(
        ids: String?,
        ownerIds: String?,
        olderThan: String?,
        newerThan: String?
    ): Int {
        logger.d { "getPlaylistCount" }
        return service.getPlaylistCount(ids, ownerIds, olderThan, newerThan)
    }

    suspend fun deletePlaylist(playlistId: String): Boolean {
        logger.d { "deletePlaylist" }
        return service.deletePlaylist(playlistId).status.value == 204
    }

    suspend fun addSongToPlaylist(songId: String, playlistId: String): Boolean {
        logger.d { "addSongToPlaylist" }
        return service.addSongToPlaylist(songId, playlistId).status.value == 204
    }

    suspend fun getPlaylistSongs(playlistId: String): List<Song> {
        logger.d { "getPlaylistSongs" }
        return service.getPlaylistSongs(playlistId)
    }

    suspend fun deleteSongFromPlaylist(songId: String, playlistId: String): Boolean {
        logger.d { "deleteSongFromPlaylist" }
        return service.deleteSongFromPlaylist(songId, playlistId).status.value == 204
    }
}