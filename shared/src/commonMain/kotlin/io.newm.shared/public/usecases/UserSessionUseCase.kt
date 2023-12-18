package io.newm.shared.public.usecases

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * `UserSessionUseCase` defines the contract for managing user session states in newm mobile application.
 *
 * This interface provides methods for checking the current login status of a user,
 * observing changes in the user's login state in a reactive manner, and handling user logout functionality.
 */
interface UserSessionUseCase {

    /**
     * Checks whether the user is currently logged in.
     *
     * This method is a straightforward way to determine the user's current authentication status.
     *
     * @return Boolean - Returns `true` if the user is currently logged in, otherwise returns `false`.
     */
    fun isLoggedIn(): Boolean

    /**
     * Provides a stream (Flow) of the user login state changes.
     *
     * This method is useful for observing the user's login state reactively. It allows components
     * or other parts of the application to respond to changes in the user's login status.
     *
     * @return Flow<Boolean> - A flow that emits the user's login state. It emits `true` if the user is logged in,
     *                         and `false` otherwise.
     */
    fun isLoggedInFlow(): Flow<Boolean>

}

class UserSessionUseCaseProvider(): KoinComponent {
    private val userSessionUseCase: UserSessionUseCase by inject()

    fun get(): UserSessionUseCase {
        return this.userSessionUseCase
    }
}