@file:OptIn(ExperimentalCoroutinesApi::class)

package io.newm.shared.internal.implementations

import io.newm.shared.internal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.internal.repositories.NFTRepository
import io.newm.shared.internal.repositories.WalletRepository
import io.newm.shared.public.models.WalletConnection
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.DisconnectWalletUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.component.KoinComponent
import shared.Notification
import shared.postNotification
import kotlin.coroutines.cancellation.CancellationException

internal class DisconnectWalletUseCaseImpl(
    private val walletRepository: WalletRepository,
    private val nftRepository: NFTRepository,
) : DisconnectWalletUseCase, KoinComponent {

//TODO: don't return success value, throw error
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun disconnect(walletConnectionId: String?) {
        mapErrorsSuspend {
            if (walletConnectionId != null) {
                walletRepository.disconnectWallet(walletConnectionId)
            } else {
                walletRepository.getWalletConnectionsCache()
                    .mapLatest { connections: List<WalletConnection> ->
                        connections.forEach { connection ->
                            walletRepository.disconnectWallet(connection.id)
                        }
                    }
                    .collect { }
            }
            nftRepository.deleteAllTracksNFTsCache()
            postNotification(Notification.walletConnectionStateChanged)
        }
    }
}
