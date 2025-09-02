package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.GetWalletConnectionsUseCase
import io.newm.shared.commonPublic.usecases.HasWalletConnectionsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

internal class HasWalletConnectionsUseCaseImpl(
    private val getWalletConnectionsUseCase: GetWalletConnectionsUseCase
) : HasWalletConnectionsUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override fun hasWalletConnectionsFlow(): Flow<Boolean> {
        return getWalletConnectionsUseCase.getWalletConnectionsFromCacheFlow().map { connections ->
            connections.isNotEmpty()
        }
    }

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun hasWalletConnections(): Boolean {
        return mapErrorsSuspend {
            hasWalletConnectionsFlow().first()
        }
    }
}