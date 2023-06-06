package io.newm.shared.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.models.Playlist
import io.newm.shared.models.Song
import io.newm.shared.services.PlaylistAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface PlaylistRepository {
    suspend fun createPlaylist(name: String): String
    suspend fun updatePlaylistName(newPlaylistName: String, playlistId: String): Boolean
    suspend fun getPlaylist(playlistId: String): Playlist
    suspend fun getCurrentUserPlaylists(
        offset: Int? = null,
        limit: Int? = null,
        ids: String? = null,
        ownerIds: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): List<Playlist>

    suspend fun getPlaylistCount(
        ids: String? = null,
        ownerIds: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): Int

    suspend fun deletePlaylist(playlistId: String): Boolean
    suspend fun addSongToPlaylist(songId: String, playlistId: String): Boolean
    suspend fun getPlaylistSongs(playlistId: String): List<Song>
    suspend fun deleteSongFromPlaylist(songId: String, playlistId: String): Boolean
}

internal class PlaylistRepositoryImpl : KoinComponent, PlaylistRepository {

    private val service: PlaylistAPI by inject()
    private val logger = Logger.withTag("NewmKMM-PlaylistRepository")

    override suspend fun createPlaylist(name: String): String {
        logger.d { "createPlaylist" }
        return service.createPlaylist(name)
    }

    override suspend fun updatePlaylistName(newPlaylistName: String, playlistId: String): Boolean {
        logger.d { "updatePlaylistName" }
        return service.updatePlaylistName(newPlaylistName, playlistId).status.value == 204
    }

    override suspend fun getPlaylist(playlistId: String): Playlist {
        logger.d { "getPlaylist" }
        return service.getPlaylist(playlistId)
    }

    override suspend fun getCurrentUserPlaylists(
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

    override suspend fun getPlaylistCount(
        ids: String?,
        ownerIds: String?,
        olderThan: String?,
        newerThan: String?
    ): Int {
        logger.d { "getPlaylistCount" }
        return service.getPlaylistCount(ids, ownerIds, olderThan, newerThan)
    }

    override suspend fun deletePlaylist(playlistId: String): Boolean {
        logger.d { "deletePlaylist" }
        return service.deletePlaylist(playlistId).status.value == 204
    }

    override suspend fun addSongToPlaylist(songId: String, playlistId: String): Boolean {
        logger.d { "addSongToPlaylist" }
        return service.addSongToPlaylist(songId, playlistId).status.value == 204
    }

    override suspend fun getPlaylistSongs(playlistId: String): List<Song> {
        logger.d { "getPlaylistSongs" }
        return service.getPlaylistSongs(playlistId)
    }

    override suspend fun deleteSongFromPlaylist(songId: String, playlistId: String): Boolean {
        logger.d { "deleteSongFromPlaylist" }
        return service.deleteSongFromPlaylist(songId, playlistId).status.value == 204
    }

}