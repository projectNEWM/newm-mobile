package io.newm.shared.public.usecases

import io.newm.shared.public.models.NFTTrack
import io.newm.shared.public.models.error.KMMException
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException


/**
 * `WalletNFTTracksUseCase` defines the contract for retrieving Non-Fungible Token (NFT) tracks associated with a wallet.
 *
 * This interface provides methods to retrieve a list of NFT tracks either as a continuous stream (Flow)
 * or as a single suspending function call.
 */
interface WalletNFTTracksUseCase {
    val walletSynced: Flow<Boolean>


    /**
     * Provides a continuous stream (Flow) of all NFT tracks associated with the wallet
     * that are not stream tokens.
     *
     * This method is useful for observing changes in the list of NFT tracks in a reactive manner.
     * It ensures that any updates to the NFT tracks are reflected in real-time.
     *
     * @return Flow<List<NFTTrack>> - A flow emitting the list of NFT tracks associated with the wallet.
     */
    fun getAllCollectableTracksFlow(): Flow<List<NFTTrack>>

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getAllCollectableTracks(): List<NFTTrack>

    /**
     * Provides a continuous stream (Flow) of all NFT tracks associated with the wallet
     * that ARE stream tokens. Stream tokens are NFTs minted through newm studio or newm sample sales.
     *
     * This method is useful for observing changes in the list of NFT tracks in a reactive manner.
     * It ensures that any updates to the NFT tracks are reflected in real-time.
     *
     * @return Flow<List<NFTTrack>> - A flow emitting the list of NFT tracks associated with the wallet.
     */
    fun getAllStreamTokensFlow(): Flow<List<NFTTrack>>

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getAllStreamTokens(): List<NFTTrack>
	
	@Throws(KMMException::class, CancellationException::class)
	suspend fun getAllTracksFlow(): Flow<List<NFTTrack>>

	@Throws(KMMException::class, CancellationException::class)
	suspend fun getAllTracks(): List<NFTTrack>

    @Throws(KMMException::class, CancellationException::class)
    suspend fun refresh()
}

class WalletNFTTracksUseCaseProvider : KoinComponent {
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase by inject()

    fun get(): WalletNFTTracksUseCase {
        return this.walletNFTTracksUseCase
    }
}
