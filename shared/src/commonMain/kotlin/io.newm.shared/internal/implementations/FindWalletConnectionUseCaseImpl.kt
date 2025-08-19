package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrors
import io.newm.shared.internal.repositories.WalletRepository
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.FindWalletConnectionUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import kotlin.coroutines.cancellation.CancellationException

internal class FindWalletConnectionUseCaseImpl(
    private val walletRepository: WalletRepository
) : FindWalletConnectionUseCase, KoinComponent {
    @Throws(KMMException::class, CancellationException::class)
    override fun findWalletConnectionByIDFromCacheFlow(id: String): Flow<WalletConnection?> {
        return mapErrors {
            walletRepository.findWalletConnectionByID(id)
        }
    }
}