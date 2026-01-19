package io.newm.shared.commonInternal.repositories

import io.newm.shared.commonInternal.api.PlaylistAPI
import io.newm.shared.commonPublic.models.Playlist
import io.newm.shared.commonPublic.models.Song
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class PlaylistRepository : KoinComponent {
    private val service: PlaylistAPI by inject()

    suspend fun createPlaylist(name: String): String = service.createPlaylist(name)

    suspend fun updatePlaylistName(
        newPlaylistName: String,
        playlistId: String,
    ): Boolean = service.updatePlaylistName(newPlaylistName, playlistId).status.value == 204

    suspend fun getPlaylist(playlistId: String): Playlist = service.getPlaylist(playlistId)

    suspend fun getCurrentUserPlaylists(
        offset: Int?,
        limit: Int?,
        ids: String?,
        ownerIds: String?,
        olderThan: String?,
        newerThan: String?,
    ): List<Playlist> = service.getCurrentUserPlaylists(offset, limit, ids, ownerIds, olderThan, newerThan)

    suspend fun getPlaylistCount(
        ids: String?,
        ownerIds: String?,
        olderThan: String?,
        newerThan: String?,
    ): Int = service.getPlaylistCount(ids, ownerIds, olderThan, newerThan)

    suspend fun deletePlaylist(playlistId: String): Boolean = service.deletePlaylist(playlistId).status.value == 204

    suspend fun addSongToPlaylist(
        songId: String,
        playlistId: String,
    ): Boolean = service.addSongToPlaylist(songId, playlistId).status.value == 204

    suspend fun getPlaylistSongs(playlistId: String): List<Song> = service.getPlaylistSongs(playlistId)

    suspend fun deleteSongFromPlaylist(
        songId: String,
        playlistId: String,
    ): Boolean = service.deleteSongFromPlaylist(songId, playlistId).status.value == 204
}
