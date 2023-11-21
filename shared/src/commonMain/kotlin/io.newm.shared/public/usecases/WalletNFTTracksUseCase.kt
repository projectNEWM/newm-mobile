package io.newm.shared.public.usecases

import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


/**
 * `WalletNFTTracksUseCase` defines the contract for retrieving Non-Fungible Token (NFT) tracks associated with a wallet.
 *
 * This interface provides methods to retrieve a list of NFT tracks either as a continuous stream (Flow)
 * or as a single suspending function call.
 */
interface WalletNFTTracksUseCase {

    /**
     * Provides a continuous stream (Flow) of all NFT tracks associated with the wallet.
     *
     * This method is useful for observing changes in the list of NFT tracks in a reactive manner.
     * It ensures that any updates to the NFT tracks are reflected in real-time.
     *
     * @return Flow<List<NFTTrack>> - A flow emitting the list of NFT tracks associated with the wallet.
     */
    fun getAllNFTTracksFlow(): Flow<List<NFTTrack>>

    /**
     * Fetches the complete list of NFT tracks associated with the wallet as a single operation.
     *
     * This suspending function provides a snapshot of all NFT tracks at the time of the call,
     * suitable for use cases where a continuous stream is not necessary.
     *
     * @return List<NFTTrack> - A list of NFT tracks associated with the wallet.
     * @throws KMMException if there is an issue in the process of fetching the NFT tracks.
     * @throws CancellationException if the operation is cancelled during execution.
     */
    @Throws(KMMException::class, CancellationException::class)
    suspend fun getAllNFTTracks(): List<NFTTrack>
}

class WalletNFTSongsUseCaseProvider(): KoinComponent {
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase by inject()

    fun get(): WalletNFTTracksUseCase {
        return this.walletNFTTracksUseCase
    }
}