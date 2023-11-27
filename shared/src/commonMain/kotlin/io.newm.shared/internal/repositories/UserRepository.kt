package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.public.models.User
import io.newm.shared.internal.services.UserAPI
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

internal class UserRepository : KoinComponent {

    private val service: UserAPI by inject()
    private val logger = Logger.withTag("NewmKMM-UserRepository")

    @Throws(KMMException::class, CancellationException::class)
    suspend fun fetchLoggedInUserDetails(): User {
        logger.d { "getCurrentUser()" }
        return service.getCurrentUser()
    }

    suspend fun getUserById(userId: String): User {
        logger.d { "getUserById(userId)" }
        return service.getUserById(userId)
    }

    suspend fun getUsers(
        offset: Int?,
        limit: Int?,
        ids: String?,
        roles: String?,
        genres: String?,
        olderThan: String?,
        newerThan: String?
    ): List<User> {
        logger.d { "getUsers List" }
        return service.getUsers(offset, limit, ids, roles, genres, olderThan, newerThan)
    }

    suspend fun getUserCount(): Int {
        logger.d { "getUserCount" }
        return service.getUserCount().count
    }

    suspend fun deleteCurrentUser(): Boolean {
        logger.d { "deleteCurrentUser" }
        return service.deleteCurrentUser().status.value == 204
    }
}