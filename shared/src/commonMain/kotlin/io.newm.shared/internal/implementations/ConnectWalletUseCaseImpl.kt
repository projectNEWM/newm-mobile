@file:OptIn(ExperimentalCoroutinesApi::class)

package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrors
import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.CardanoWalletRepository
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.usecases.ConnectWalletUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import shared.Notification
import shared.postNotification

internal class ConnectWalletUseCaseImpl(
    private val cardanoWalletRepository: CardanoWalletRepository
) : ConnectWalletUseCase {
    override suspend fun connect(walletConnectionId: String) {
        mapErrorsSuspend {
            cardanoWalletRepository.connectWallet(walletConnectionId)
            postNotification(Notification.walletConnectionStateChanged)
        }
    }

    override suspend fun disconnect(walletConnectionId: String? ) {
        mapErrorsSuspend {
            if (walletConnectionId != null) {
                cardanoWalletRepository.disconnectWallet(walletConnectionId)
            } else {
                cardanoWalletRepository.getWalletConnectionsCache()
                    .mapLatest { connections: List<WalletConnection> ->
                        connections.forEach { connection ->
                            cardanoWalletRepository.disconnectWallet(connection.id)
                        }
                    }
                    .collect{ } // This will trigger the flow execution
            }
        }
        cardanoWalletRepository.deleteAllTracksNFTsCache()
        postNotification(Notification.walletConnectionStateChanged)
    }

    override fun hasWalletConnectionsFlow(): Flow<Boolean> {
        return getCacheWalletConnectionsFlow().map { connections ->
            connections.isNotEmpty()
        }
    }

    override suspend fun getWalletConnectionsNetwork() {
        cardanoWalletRepository.getWalletConnectionsNetwork().isNotEmpty()
    }

    override fun getCacheWalletConnectionsFlow(): Flow<List<WalletConnection>> {
        return mapErrors {
            cardanoWalletRepository.getWalletConnectionsCache()
        }
    }

    override suspend fun hasWalletConnections(): Boolean {
        return mapErrorsSuspend {
            return@mapErrorsSuspend hasWalletConnectionsFlow().first()
        }
    }

    override suspend fun getWalletConnections(): List<WalletConnection> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend getCacheWalletConnectionsFlow().first()
        }
    }
}
