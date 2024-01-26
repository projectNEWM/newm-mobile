package io.newm.shared.internal.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.public.models.Playlist
import io.newm.shared.public.models.Song
import io.newm.shared.public.models.error.KMMException
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class PlaylistAPI(networkClient: NetworkClientFactory) : KoinComponent {

    private val authClient: HttpClient  = networkClient.httpClient()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun createPlaylist(playlistName: String): String =
        authClient.post("/v1/playlists") {
            contentType(ContentType.Application.Json)
            setBody(NamePlayListRequest(name = playlistName))
        }.body<NamePlayListResponse>().playlistId

    @Throws(KMMException::class, CancellationException::class)
    suspend fun updatePlaylistName(newPlaylistName: String, playlistId: String) =
        authClient.patch("/v1/playlists/$playlistId") {
            contentType(ContentType.Application.Json)
            setBody(NamePlayListRequest(name = newPlaylistName))
        }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getPlaylist(playlistId: String) =
        authClient.get("/v1/playlists/$playlistId") {
            contentType(ContentType.Application.Json)
        }.body<Playlist>()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getCurrentUserPlaylists(
        offset: Int? = null,
        limit: Int? = null,
        ids: String? = null,
        ownerIds: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): List<Playlist> = authClient.get("/v1/playlists") {
        contentType(ContentType.Application.Json)
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
    ): Int = authClient.get("/v1/playlists/count") {
        contentType(ContentType.Application.Json)
        parameter("ids", ids)
        parameter("ownerIds", ownerIds)
        parameter("olderThan", olderThan)
        parameter("newerThan", newerThan)
    }.body<PlaylistCount>().count

    @Throws(KMMException::class, CancellationException::class)
    suspend fun deletePlaylist(playlistId: String) =
        authClient.delete("/v1/playlists/$playlistId") {
            contentType(ContentType.Application.Json)
        }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun addSongToPlaylist(playlistId: String, songId: String) =
        authClient.put("/v1/playlists/$playlistId/songs/$songId") {
            contentType(ContentType.Application.Json)
        }

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getPlaylistSongs(playlistId: String, offset: Int = 0, limit: Int = 25): List<Song> =
        authClient.get("/v1/playlists/$playlistId/songs") {
            contentType(ContentType.Application.Json)
            parameter("offset", offset)
            parameter("limit", limit)
        }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun deleteSongFromPlaylist(playlistId: String, songId: String) =
        authClient.delete("/v1/playlists/$playlistId/songs/$songId") {
            contentType(ContentType.Application.Json)
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