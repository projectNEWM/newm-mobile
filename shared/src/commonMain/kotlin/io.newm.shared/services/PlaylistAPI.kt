package io.newm.shared.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.newm.shared.db.NewmDatabaseWrapper
import io.newm.shared.login.repository.KMMException
import io.newm.shared.models.Genre
import io.newm.shared.models.Playlist
import io.newm.shared.models.Song
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

internal class PlaylistAPI(private val client: HttpClient) : KoinComponent {

    private val db: NewmDatabaseWrapper by inject()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun createPlaylist(playlistName: String): String =
        client.post("/v1/playlists") {
            contentType(ContentType.Application.Json)
            bearerAuth(
                db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
            )
            setBody(NamePlayListRequest(name = playlistName))
        }.body<NamePlayListResponse>().playlistId

    //TODO: Find the response type
    @Throws(KMMException::class, CancellationException::class)
    suspend fun updatePlaylistName(newPlaylistName: String, playlistId: String) =
        client.patch("/v1/playlists/$playlistId") {
            contentType(ContentType.Application.Json)
            bearerAuth(
                db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
            )
            setBody(NamePlayListRequest(name = newPlaylistName))
        }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getPlaylist(playlistId: String) =
        client.get("/v1/playlists/$playlistId") {
            contentType(ContentType.Application.Json)
            bearerAuth(
                db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
            )
        }.body<Playlist>()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getCurrentUserPlaylists(
        offset: Int? = null,
        limit: Int? = null,
        ids: String? = null,
        ownerIds: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): List<Playlist> = client.get("/v1/playlists") {
        contentType(ContentType.Application.Json)
        bearerAuth(
            db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
        )
        parameter("offset", offset)
        parameter("limit", limit)
        parameter("ids", ids)
        parameter("ownerIds", ownerIds)
        parameter("olderThan", olderThan)
        parameter("newerThan", newerThan)
    }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getPlaylistCount(
        ids: String? = null,
        ownerIds: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): Int = client.get("/v1/playlists/count") {
        contentType(ContentType.Application.Json)
        bearerAuth(
            db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
        )
        parameter("ids", ids)
        parameter("ownerIds", ownerIds)
        parameter("olderThan", olderThan)
        parameter("newerThan", newerThan)
    }.body<PlaylistCount>().count

    @Throws(KMMException::class, CancellationException::class)
    suspend fun deletePlaylist(playlistId: String) =
        client.delete("/v1/playlists/$playlistId") {
            contentType(ContentType.Application.Json)
            bearerAuth(
                db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
            )
        }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun addSongToPlaylist(playlistId: String, songId: String) =
        client.put("/v1/playlists/$playlistId/songs/$songId") {
            contentType(ContentType.Application.Json)
            bearerAuth(
                db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
            )
        }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getPlaylistSongs(playlistId: String, offset: Int = 0, limit: Int = 25): List<Song> =
        client.get("/v1/playlists/$playlistId/songs") {
            contentType(ContentType.Application.Json)
            bearerAuth(
                db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
            )
            parameter("offset", offset)
            parameter("limit", limit)
        }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun deleteSongFromPlaylist(playlistId: String, songId: String) =
        client.delete("/v1/playlists/$playlistId/songs/$songId") {
            contentType(ContentType.Application.Json)
            bearerAuth(
                db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
            )
        }

}

@Serializable
data class NamePlayListRequest(
    val name: String
)

@Serializable
data class NamePlayListResponse(
    val playlistId: String
)

@Serializable
internal data class PlaylistCount(
    val count: Int
)