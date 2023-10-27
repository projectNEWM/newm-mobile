package io.newm.feature.musicplayer.repository

import android.content.Context
import android.net.Uri
import io.newm.feature.musicplayer.models.Playlist
import io.newm.feature.musicplayer.models.Track
import io.newm.shared.repositories.testdata.MockSongModel
import io.newm.shared.repositories.testdata.savedSongModels
import io.newm.feature.musicplayer.R

interface MusicRepository {
    // TODO make suspended
    fun fetchTrack(trackId: String): Track
    fun fetchPlaylist(playlistId: String): Playlist
    fun saveTrack(track: Track)
    fun savePlaylist(playlist: Playlist)
}

class MockMusicRepository(
    private val context: Context,
) : MusicRepository {
    override fun fetchTrack(trackId: String): Track {
        // TODO find by id
        val songModel =
            savedSongModels.find { "${it.assetId}" == trackId } ?: savedSongModels.first()

        return Track(
            id = songModel.title,
            title = songModel.title,
            url = songModel.getAssetSourceSongURI(context).toString(),
            artist = songModel.artist,
        )
    }

    override fun fetchPlaylist(playlistId: String): Playlist {
        // TODO find by id
        val tracks = savedSongModels.map { songModel ->
            Track(
                id = songModel.title,
                title = songModel.title,
                url = songModel.getAssetSourceSongURI(context).toString(),
                artist = songModel.artist,
            )
        }
        return Playlist(tracks = tracks)
    }

    override fun saveTrack(track: Track) {
        TODO("Not yet implemented")
    }

    override fun savePlaylist(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    private fun MockSongModel.getAssetSourceSongURI(context: Context): Uri {
        val assetName = when (this.assetId) {
            1 -> R.raw.song1
            2 -> R.raw.song2
            3 -> R.raw.song3
            4 -> R.raw.song4
            5 -> R.raw.song5
            6 -> R.raw.song6
            else -> R.raw.song0
        }
        return Uri.parse("android.resource://${context.packageName}/$assetName")
    }

}
