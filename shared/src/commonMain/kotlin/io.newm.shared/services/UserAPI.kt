package io.newm.shared.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.newm.shared.db.NewmDatabaseWrapper
import io.newm.shared.login.repository.KMMException
import io.newm.shared.models.User
import io.newm.shared.models.UserCount
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

internal class UserAPI(private val client: HttpClient) : KoinComponent {

    private val db: NewmDatabaseWrapper by inject()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getCurrentUser(): User = client.get("/v1/users/me") {
        contentType(ContentType.Application.Json)
        bearerAuth(
            db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
        )
    }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getUserById(userId: String): User = client.get("/v1/users/$userId") {
        contentType(ContentType.Application.Json)
        bearerAuth(
            db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString()
        )
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
    ): List<User> = client.get("/v1/users") {
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
    suspend fun getUserCount(): UserCount = client.get("/v1/users/count") {
        contentType(ContentType.Application.Json)
    }.body()

    @Throws(KMMException::class, CancellationException::class)
    suspend fun deleteCurrentUser() = client.delete("/v1/users/me") {
        contentType(ContentType.Application.Json)
        bearerAuth(db.instance?.newmAuthQueries?.selectAll()?.executeAsOne()?.access_token.toString())
    }
}