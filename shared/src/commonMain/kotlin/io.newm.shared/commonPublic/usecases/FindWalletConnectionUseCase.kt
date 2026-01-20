package io.newm.shared.commonPublic.usecases

import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.shared.commonPublic.models.error.KMMException
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.cancellation.CancellationException

/** Represents a use case for finding a single wallet connection by ID */
interface FindWalletConnectionUseCase {
    /**
     * Finds a single wallet connection by ID from the cache
     *
     * @param id The ID of the wallet connection to find
     * @return A [Flow] of the wallet connection, or null if not found
     * @throws [KMMException] if an error occurs
     */
    @Throws(KMMException::class, CancellationException::class)
    fun findWalletConnectionByIDFromCacheFlow(id: String): Flow<WalletConnection?>
}
