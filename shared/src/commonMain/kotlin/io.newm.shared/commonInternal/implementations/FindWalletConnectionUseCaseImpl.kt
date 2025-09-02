package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrors
import io.newm.shared.commonInternal.repositories.WalletRepository
import io.newm.shared.commonPublic.models.WalletConnection
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.FindWalletConnectionUseCase
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