package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import io.newm.shared.internal.db.NewmDatabaseWrapper
import io.newm.shared.public.models.User
import io.newm.shared.internal.services.UserAPI
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

internal class UserRepository(
    dbWrapper: NewmDatabaseWrapper,
    private val scope: CoroutineScope,
) : KoinComponent {

    private val service: UserAPI by inject()
    private val logger = Logger.withTag("NewmKMM-UserRepository")
    private val database = dbWrapper.instance ?: throw KMMException("Database not initialized")

    @Throws(KMMException::class, CancellationException::class)
    suspend fun fetchLoggedInUserDetails(): User {
        logger.d { "getCurrentUser()" }
        return service.getCurrentUser()
    }


    fun fetchUserDetailsFlow() = database.userQueries.getAnyUser()
        .asFlow()
        .mapToOneOrNull() // This will emit either one user or null
        .onStart {
            if (database.userQueries.getAnyUser().executeAsOneOrNull() == null ) {
                logger.d { "No Users found in DB, fetching from network" }
                scope.launch {
                    val user = fetchLoggedInUserDetails()
                    database.userQueries.insertUser(
                        id = user.id,
                        createdAt = user.createdAt,
                        oauthType = user.oauthType,
                        oauthId = user.oauthId,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        nickname = user.nickname,
                        pictureUrl = user.pictureUrl,
                        bannerUrl = user.bannerUrl,
                        websiteUrl = user.websiteUrl,
                        twitterUrl = user.twitterUrl,
                        instagramUrl = user.instagramUrl,
                        location = user.location,
                        role = user.role,
                        genre = user.genre,
                        biography = user.biography,
                        walletAddress = user.walletAddress,
                        email = user.email,
                        companyName = user.companyName,
                        companyLogoUrl = user.companyLogoUrl,
                        companyIpRights = user.companyIpRights,
                        verificationStatus = user.verificationStatus,
                    )
                }
            }
        }.map { dbUser ->
            dbUser?.let {
                User(
                    id = dbUser.id,
                    createdAt = dbUser.createdAt,
                    oauthType = dbUser.oauthType,
                    oauthId = dbUser.oauthId,
                    firstName = dbUser.firstName,
                    lastName = dbUser.lastName,
                    nickname = dbUser.nickname,
                    pictureUrl = dbUser.pictureUrl,
                    bannerUrl = dbUser.bannerUrl,
                    websiteUrl = dbUser.websiteUrl,
                    twitterUrl = dbUser.twitterUrl,
                    instagramUrl = dbUser.instagramUrl,
                    location = dbUser.location,
                    role = dbUser.role,
                    genre = dbUser.genre,
                    biography = dbUser.biography,
                    walletAddress = dbUser.walletAddress,
                    email = dbUser.email,
                    companyName = dbUser.companyName,
                    companyLogoUrl = dbUser.companyLogoUrl,
                    companyIpRights = dbUser.companyIpRights,
                    verificationStatus = dbUser.verificationStatus,
                )
            }
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