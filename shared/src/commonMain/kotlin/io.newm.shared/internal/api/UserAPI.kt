package io.newm.shared.internal.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.newm.shared.di.NetworkClientFactory
import io.newm.shared.internal.api.models.UserProfileUpdateRequest
import io.newm.shared.public.models.User
import io.newm.shared.public.models.error.KMMException
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

internal class UserAPI(networkClient: NetworkClientFactory) : KoinComponent {

    private val authHttpClient: HttpClient  = networkClient.authHttpClient()

    suspend fun getCurrentUser(): User = authHttpClient.get("/v1/users/me") {
        contentType(ContentType.Application.Json)
    }.body()

    suspend fun getUserById(userId: String): User = authHttpClient.get("/v1/users/$userId") {
        contentType(ContentType.Application.Json)
    }.body()

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

    suspend fun deleteCurrentUser() = authHttpClient.delete("/v1/users/me") {
        contentType(ContentType.Application.Json)
    }

    suspend fun updateUserProfile(userProfileUpdateRequest: UserProfileUpdateRequest) {
        try {
            authHttpClient.patch("/v1/users/me") {
                contentType(ContentType.Application.Json)
                setBody(userProfileUpdateRequest)
            }
        } catch (e: ClientRequestException) {
            //TODO:  We need api level errors returned from the api (not http level) so we know what went wrong.  401 here can mean "bad auth token" or "wrong password"
            val errorBody: APIErrorResponse = e.response.body()
            throw when (errorBody.code) {
                401 -> {
                    UserAPIException.WrongPassword(errorBody.cause)
                }
                422 -> {
                    UserAPIException.MalformedPassword(errorBody.cause)
                }
                else -> {
                    e
                }
            }
        }
    }
}

@Serializable
internal data class UserCount(
    val count: Int
)

sealed class UserAPIException(message: String): KMMException(message) {
    data class WrongPassword(override val message: String) : UserAPIException(message)
    data class MalformedPassword(override val message: String) : UserAPIException(message)
}

//TODO: this is currently how the server has errors formatted, but we need to fix it.  That code should be application level, not http.
@Serializable
class APIErrorResponse (
    val code: Int,
    val description: String,
    val cause: String,
)