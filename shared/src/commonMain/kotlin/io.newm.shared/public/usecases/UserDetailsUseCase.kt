package io.newm.shared.public.usecases

import io.newm.shared.public.models.User
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `UserDetailsUseCase` defines the contract for retrieving details of the currently logged-in user.
 *
 * This interface is designed to encapsulate the functionality for fetching user-specific information,
 * ensuring that the implementation details are abstracted away from the usage context.
 */
interface UserDetailsUseCase {

    /**
     * Fetches the details of the currently logged-in user.
     *
     * This method is responsible for retrieving comprehensive information about the user who is currently
     * authenticated in the application. It returns a [User] object containing the user details.
     *
     * @return User - An object containing details of the currently authenticated user.
     * @throws KMMException if there is an issue in the process of fetching user details, such as network errors.
     * @throws CancellationException if the operation is cancelled during execution.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun fetchLoggedInUserDetails(): User

    /**
     * Fetches the details of the currently logged-in user.
     *
     * This method is responsible for retrieving comprehensive information about the user who is currently
     * authenticated in the application. It returns a [User] object containing the user details.
     *
     * @return User - An object containing details of the currently authenticated user.
     * @throws KMMException if there is an issue in the process of fetching user details, such as network errors.
     * @throws CancellationException if the operation is cancelled during execution.
     */
    fun fetchLoggedInUserDetailsFlow(): Flow<User?>
}

class UserDetailsUseCaseProvider() : KoinComponent {
    private val userDetailsUseCase: UserDetailsUseCase by inject()

    fun get(): UserDetailsUseCase {
        return this.userDetailsUseCase
    }
}