package io.newm.shared.internal.repositories

import io.newm.shared.public.models.Playlist
import io.newm.shared.public.models.Song
import io.newm.shared.internal.api.PlaylistAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class PlaylistRepository : KoinComponent {

    private val service: PlaylistAPI by inject()

    suspend fun createPlaylist(name: String): String {
        return service.createPlaylist(name)
    }

    suspend fun updatePlaylistName(newPlaylistName: String, playlistId: String): Boolean {
        return service.updatePlaylistName(newPlaylistName, playlistId).status.value == 204
    }

    suspend fun getPlaylist(playlistId: String): Playlist {
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
        return service.getCurrentUserPlaylists(offset, limit, ids, ownerIds, olderThan, newerThan)
    }

    suspend fun getPlaylistCount(
        ids: String?,
        ownerIds: String?,
        olderThan: String?,
        newerThan: String?
    ): Int {
        return service.getPlaylistCount(ids, ownerIds, olderThan, newerThan)
    }

    suspend fun deletePlaylist(playlistId: String): Boolean {
        return service.deletePlaylist(playlistId).status.value == 204
    }

    suspend fun addSongToPlaylist(songId: String, playlistId: String): Boolean {
        return service.addSongToPlaylist(songId, playlistId).status.value == 204
    }

    suspend fun getPlaylistSongs(playlistId: String): List<Song> {
        return service.getPlaylistSongs(playlistId)
    }

    suspend fun deleteSongFromPlaylist(songId: String, playlistId: String): Boolean {
        return service.deleteSongFromPlaylist(songId, playlistId).status.value == 204
    }
}