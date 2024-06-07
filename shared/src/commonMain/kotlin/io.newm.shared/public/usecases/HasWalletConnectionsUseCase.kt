package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `HasWalletConnectionsUseCase` defines the contract for checking if there are any wallet connections available in the cache.
 */
interface HasWalletConnectionsUseCase {

    /**
     * Checks if there are any wallet connections available in the cache.
     *
     * @return A [Flow] emitting a boolean indicating if there are wallet connections in the cache.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    fun hasWalletConnectionsFlow(): Flow<Boolean>

    /**
     * Checks if there are any wallet connections available in the cache.
     *
     * @return True if there are wallet connections in the cache, false otherwise.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun hasWalletConnections(): Boolean
}

class HasWalletConnectionsUseCaseProvider : KoinComponent {
    private val hasWalletConnectionsUseCase: HasWalletConnectionsUseCase by inject()

    fun get(): HasWalletConnectionsUseCase {
        return this.hasWalletConnectionsUseCase
    }
}
