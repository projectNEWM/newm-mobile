package io.newm.shared.public.usecases

import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * `SyncWalletConnectionsUseCase` defines the contract for synchronizing wallet connections from the network to the device.
 */
interface SyncWalletConnectionsUseCase {

    /**
     * Synchronizes wallet connections from the network to the device's database.
     * This method should be called at least once per app launch or after connecting a wallet
     * to ensure the database is seeded from the network.
     *
     * @return A list of [WalletConnection] fetched from the network.
     * @throws KMMException If an application-specific error occurs.
     * @throws CancellationException If the operation is cancelled.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun syncWalletConnectionsFromNetworkToDevice(): List<WalletConnection>
}

class SyncWalletConnectionsUseCaseProvider : KoinComponent {
    private val syncWalletConnectionsUseCase: SyncWalletConnectionsUseCase by inject()

    fun get(): SyncWalletConnectionsUseCase {
        return this.syncWalletConnectionsUseCase
    }
}
