package io.newm.shared.commonPublic.usecases

import io.newm.shared.commonPublic.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `UpdateWalletNameUseCase` defines the contract for updating a wallet connection's display name.
 */
interface UpdateWalletNameUseCase {
    /**
     * Updates the display name of a wallet connection.
     *
     * @param walletConnectionId The ID of the wallet connection to update.
     * @param name The new display name for the wallet connection.
     * @return true if the update was successful, false otherwise.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun updateName(
        walletConnectionId: String,
        name: String,
    ): Boolean
}

class UpdateWalletNameUseCaseProvider : KoinComponent {
    private val updateWalletNameUseCase: UpdateWalletNameUseCase by inject()

    fun get(): UpdateWalletNameUseCase = this.updateWalletNameUseCase
}
