package io.newm.shared.internal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.public.models.Genre
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class GenresAPI(
    networkClient: NetworkClientFactory
) : KoinComponent {
    private val authClient: HttpClient  = networkClient.authHttpClient()

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
    ) = authClient.get("/v1/distribution/genres") {
        contentType(ContentType.Application.Json)
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
