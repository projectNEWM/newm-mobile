package io.newm.shared.public.usecases

import io.newm.shared.public.models.WalletConnection
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * `ConnectWalletUseCase` defines the contract for managing wallet connections.
 *
 * This interface provides methods to connect to a wallet using a wallet connection id,
 * disconnect from the wallet, and check if a wallet is currently connected.
 */
interface ConnectWalletUseCase {

    /**
     * Connects to a wallet using the specified [walletConnectionId].
     *
     * @param walletConnectionId The identifier of the wallet connection to be established.
     */
    fun connect(walletConnectionId: String)

    /**
     * Disconnects from the wallet identified by [walletConnectionId].
     *
     * If [walletConnectionId] is null, disconnects from all connected wallets.
     *
     * @param walletConnectionId The identifier of the wallet connection to be terminated,
     * or null to disconnect from all connected wallets.
     */
    fun disconnect(walletConnectionId: String? = null)

    /**
     * Retrieves a flow of lists of [WalletConnection]s representing the currently connected wallets.
     *
     * @return A flow emitting lists of [WalletConnection]s.
     */
    fun getWalletConnectionsFlow(): Flow<List<WalletConnection>>

    /**
     * Retrieves a flow indicating whether any wallet connections exist.
     *
     * @return A flow emitting `true` if there are wallet connections, `false` otherwise.
     */
    fun hasWalletConnectionsFlow(): Flow<Boolean>

    /**
     * Retrieves a list of [WalletConnection]s representing the currently connected wallets.
     *
     * @return A list of [WalletConnection]s.
     */
    suspend fun getWalletConnections(): List<WalletConnection>

    /**
     * Retrieves whether any wallet connections exist.
     *
     * @return `true` if there are wallet connections, `false` otherwise.
     */
    suspend fun hasWalletConnections(): Boolean
}

class ConnectWalletUseCaseProvider(): KoinComponent {
    private val connectWalletUseCase: ConnectWalletUseCase by inject()

    fun get(): ConnectWalletUseCase {
        return this.connectWalletUseCase
    }
}