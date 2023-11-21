package io.newm.shared.public.usecases

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * `ConnectWalletUseCase` defines the contract for managing wallet connections.
 *
 * This interface provides methods to connect to a wallet using an extended public key (xpub),
 * disconnect from the wallet, and check if a wallet is currently connected.
 */
interface ConnectWalletUseCase {
    /**
     * Establishes a connection to a wallet using the provided extended public key (xpub).
     *
     * This method initiates the connection process and verification of the xpub.
     *
     * @param xpub The extended public key of the wallet in a String format.
     *             It is a standard format in Cardano and other blockchains for sharing an address
     *             without exposing the private key.
     */
    fun connect(xpub: String)
    /**
     * Disconnects the currently connected cardano wallet.
     *
     * This method safely terminates the connection to the user's wallet and ensures
     * that no further readings from the wallet are possible.
     */
    fun disconnect()
    /**
     * Checks the current connection status of the wallet.
     *
     * @return Boolean - Returns `true` if a wallet is currently connected, `false` otherwise.
     */
    fun isConnected(): Boolean

    /**
     * Provides a continuous stream (Flow) of the current connection status of the wallet.
     *
     * This method is useful for observing changes in the connection status of the wallet in a reactive manner.
     * It ensures that any updates to the connection status are reflected in real-time.
     *
     * @return Flow<Boolean> - A flow emitting the current connection status of the wallet.
     */
    fun isConnectedFlow(): Flow<Boolean>
}

class ConnectWalletUseCaseProvider(): KoinComponent {
    private val connectWalletUseCase: ConnectWalletUseCase by inject()

    fun get(): ConnectWalletUseCase {
        return this.connectWalletUseCase
    }
}