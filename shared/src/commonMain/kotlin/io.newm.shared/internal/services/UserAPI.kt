package io.newm.shared.internal.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.models.User
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class UserAPI(networkClient: NetworkClientFactory) : KoinComponent {

    private val authHttpClient: HttpClient  = networkClient.authHttpClient()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getCurrentUser(): User = authHttpClient.get("/v1/users/me") {
        contentType(ContentType.Application.Json)
    }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getUserById(userId: String): User = authHttpClient.get("/v1/users/$userId") {
        contentType(ContentType.Application.Json)
    }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getUsers(
        offset: Int? = null,
        limit: Int? = null,
        ids: String? = null,
        roles: String? = null,
        genres: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): List<User> = authHttpClient.get("/v1/users") {
        contentType(ContentType.Application.Json)
        parameter("offset", offset)
        parameter("limit", limit)
        parameter("ids", ids)
        parameter("roles", roles)
        parameter("genres", genres)
        parameter("olderThan", olderThan)
        parameter("newerThan", newerThan)
    }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getUserCount(
        ids: String? = null,
        roles: String? = null,
        genres: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): UserCount = authHttpClient.get("/v1/users/count") {
        contentType(ContentType.Application.Json)
        parameter("ids", ids)
        parameter("roles", roles)
        parameter("genres", genres)
        parameter("olderThan", olderThan)
        parameter("newerThan", newerThan)
    }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun deleteCurrentUser() = authHttpClient.delete("/v1/users/me") {
        contentType(ContentType.Application.Json)
    }
}

@Serializable
internal data class UserCount(
    val count: Int
)