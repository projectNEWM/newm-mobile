package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.GetWalletConnectionsUseCase
import io.newm.shared.public.usecases.HasWalletConnectionsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
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