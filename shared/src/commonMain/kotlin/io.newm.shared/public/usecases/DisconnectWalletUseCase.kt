package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `DisconnectWalletUseCase` defines the contract for disconnecting from a wallet.
 */
interface DisconnectWalletUseCase {

    /**
     * Disconnects from a wallet. If no wallet connection ID is provided, all wallets are disconnected.
     *
     * @param walletConnectionId The ID of the wallet connection to disconnect, or null to disconnect all wallets.
     * @return True if the disconnection was successful, false otherwise.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun disconnect(walletConnectionId: String? = null): Boolean
}

class DisconnectWalletUseCaseProvider : KoinComponent {
    private val disconnectWalletUseCase: DisconnectWalletUseCase by inject()

    fun get(): DisconnectWalletUseCase {
        return this.disconnectWalletUseCase
    }
}
