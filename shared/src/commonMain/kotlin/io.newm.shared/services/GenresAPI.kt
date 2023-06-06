package io.newm.shared.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.newm.shared.db.NewmDatabaseWrapper
import io.newm.shared.login.repository.KMMException
import io.newm.shared.models.Genre
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

internal class GenresAPI(
    private val client: HttpClient
) : KoinComponent {
    private val db: NewmDatabaseWrapper by inject()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getGenres(
        offset: Int? = null,
        limit: Int? = null,
        phrase: String? = null,
        ids: String? = null,
        ownerIds: String? = null,
        genres: String? = null,
        moods: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ) = client.get("/v1/distribution/genres") {
        contentType(ContentType.Application.Json)
        bearerAuth(db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString())
        parameter("offset", offset)
        parameter("limit", limit)
        parameter("phrase", phrase)
        parameter("ids", ids)
        parameter("ownerIds", ownerIds)
        parameter("genres", genres)
        parameter("moods", moods)
        parameter("olderThan", olderThan)
        parameter("newerThan", newerThan)
    }.body<List<Genre>>()

}
