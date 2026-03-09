package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonInternal.repositories.WalletRepository
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.UpdateWalletNameUseCase
import org.koin.core.component.KoinComponent
import shared.Notification
import shared.postNotification
import kotlin.coroutines.cancellation.CancellationException

internal class UpdateWalletNameUseCaseImpl(
    private val walletRepository: WalletRepository,
) : UpdateWalletNameUseCase,
    KoinComponent {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun updateName(
        walletConnectionId: String,
        name: String,
    ): Boolean =
        mapErrorsSuspend {
            val success = walletRepository.updateWalletName(walletConnectionId, name)
            if (success) {
                postNotification(Notification.WALLET_CONNECTION_STATE_CHANGED)
            }
            success
        }
}
