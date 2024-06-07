package io.newm.shared.public.usecases

import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `ConnectWalletUseCase` defines the contract for connecting to a wallet.
 */
interface ConnectWalletUseCase {

    /**
     * Connects to a wallet using the given wallet connection ID.
     *
     * @param walletConnectionId The ID of the wallet connection.
     * @return The connected [WalletConnection] if successful, or null if an error occurs.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun connect(walletConnectionId: String): WalletConnection?
}

class ConnectWalletUseCaseProvider : KoinComponent {
    private val connectWalletUseCase: ConnectWalletUseCase by inject()

    fun get(): ConnectWalletUseCase {
        return this.connectWalletUseCase
    }
}
