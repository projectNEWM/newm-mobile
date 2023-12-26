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
     * Provides a continuous stream (Flow) of all NFT tracks associated with the wallet
     * that are not stream tokens.
     *
     * This method is useful for observing changes in the list of NFT tracks in a reactive manner.
     * It ensures that any updates to the NFT tracks are reflected in real-time.
     *
     * @return Flow<List<NFTTrack>> - A flow emitting the list of NFT tracks associated with the wallet.
     */
    fun getAllNFTTracksFlow(): Flow<List<NFTTrack>>


    /**
     * Provides a continuous stream (Flow) of all NFT tracks associated with the wallet
     * that ARE stream tokens. Stream tokens are NFTs minted through newm studio or newm sample sales.
     *
     * This method is useful for observing changes in the list of NFT tracks in a reactive manner.
     * It ensures that any updates to the NFT tracks are reflected in real-time.
     *
     * @return Flow<List<NFTTrack>> - A flow emitting the list of NFT tracks associated with the wallet.
     */
    fun getAllStreamTokens(): Flow<List<NFTTrack>>

    /**
     * Retrieves the details of a specific Non-Fungible Token (NFT) track by its unique identifier.
     *
     * This method provides access to the data of a single NFT track, including its metadata
     * such as the track's name, image URL, song URL, duration, and associated artists.
     * It is particularly useful for cases where details of a specific track are needed,
     * rather than the entire list of tracks associated with the wallet. (E.g. when displaying a track's details)
     *
     * @param id The unique identifier of the NFT track to be retrieved.
     * @return NFTTrack? - The NFT track with the specified ID, or `null` if no such track is found.
     */
    fun getNFTTrack(id: String): NFTTrack?
}

class WalletNFTSongsUseCaseProvider(): KoinComponent {
    private val walletNFTTracksUseCase: WalletNFTTracksUseCase by inject()

    fun get(): WalletNFTTracksUseCase {
        return this.walletNFTTracksUseCase
    }
}