package io.newm.shared.public.usecases

import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `GetWalletConnectionsUseCase` defines the contract for retrieving wallet connections from the cache.
 */
interface GetWalletConnectionsUseCase {

    /**
     * Retrieves a flow of wallet connections from the cache.
     *
     * @return A [Flow] emitting a list of [WalletConnection] from the cache.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    fun getWalletConnectionsFromCacheFlow(): Flow<List<WalletConnection>>

    /**
     * Retrieves a list of wallet connections from the cache.
     *
     * @return A list of [WalletConnection] from the cache.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun getWalletConnectionsFromCache(): List<WalletConnection>
}

class GetWalletConnectionsUseCaseProvider : KoinComponent {
    private val getWalletConnectionsUseCase: GetWalletConnectionsUseCase by inject()

    fun get(): GetWalletConnectionsUseCase {
        return this.getWalletConnectionsUseCase
    }
}
