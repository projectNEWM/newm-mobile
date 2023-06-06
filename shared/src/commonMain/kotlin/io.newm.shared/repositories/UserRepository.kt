package io.newm.shared.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.models.User
import io.newm.shared.services.UserAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal interface UserRepository {
    suspend fun getCurrentUser(): User
    suspend fun getUserById(userId: String): User
    suspend fun getUsers(
        offset: Int? = null,
        limit: Int? = null,
        ids: String? = null,
        roles: String? = null,
        genres: String? = null,
        olderThan: String? = null,
        newerThan: String? = null
    ): List<User>
    suspend fun getUserCount(): Int
    suspend fun deleteCurrentUser() : Boolean
}

internal class UserRepositoryImpl : KoinComponent, UserRepository {

    private val service: UserAPI by inject()
    private val logger = Logger.withTag("NewmKMM-UserRepository")

    override suspend fun getCurrentUser(): User {
        logger.d { "getCurrentUser()" }
        return service.getCurrentUser()
    }

    override suspend fun getUserById(userId: String): User {
        logger.d { "getUserById(userId)" }
        return service.getUserById(userId)
    }

    override suspend fun getUsers(
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

    override suspend fun getUserCount(): Int {
        logger.d { "getUserCount" }
        return service.getUserCount().count
    }

    override suspend fun deleteCurrentUser(): Boolean {
        logger.d { "deleteCurrentUser" }
        return service.deleteCurrentUser().status.value == 204
    }
}