package io.newm.shared.commonPublic.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a Non-Fungible Token (NFT) track.
 *
 * This class encapsulates the properties of an NFT track, including its unique identifier, name,
 * associated image and song URLs, duration, and a list of artists involved.
 *
 * @property id Unique identifier of the NFT track.
 * @property title Name of the NFT track.
 * @property imageUrl URL of the image associated with the NFT track.
 * @property audioUrl URL of the song file associated with the NFT track.
 * @property duration Duration of the song in seconds.
 * @property artists A list of artist names associated with the NFT track. Defaults to an empty list
 *   if not provided.
 * @property genres A list of genres associated with the NFT track. Defaults to an empty list if not
 *   provided.
 * @property moods A list of moods associated with the NFT track. Defaults to an empty list if not
 *   provided.
 */
@Serializable
data class NFTAllocation(
    @SerialName("id") val id: String,
    @SerialName("amount") val amount: Long,
    @SerialName("walletId") val walletId: String? = null, // Added walletId
)

@Serializable
data class NFTTrack(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("audioUrl") val audioUrl: String,
    @SerialName("duration") val duration: Long,
    @SerialName("artists") val artists: List<String> = emptyList(),
    @SerialName("genres") val genres: List<String>,
    @SerialName("moods") val moods: List<String> = emptyList(),
    @SerialName("amount") val amount: Long,
    @SerialName("chainType") val chainType: ChainType,
    @SerialName("chainMetadata") val chainMetadata: ChainMetadata,
    @SerialName("allocations") val allocations: List<NFTAllocation> = emptyList(),
    val isDownloaded: Boolean = false,
)

fun NFTTrack.hasAllocationForWallet(walletId: String): Boolean = allocations.any { it.id == walletId || it.walletId == walletId }

@Serializable sealed class ChainMetadata

@Serializable
@SerialName("io.newm.server.features.nftsong.model.NftChainMetadata.Cardano")
data class CardanoChainMetadata(
    val fingerprint: String,
    val policyId: String,
    val assetName: String,
    val isStreamToken: Boolean,
) : ChainMetadata()

@Serializable
@SerialName("io.newm.server.features.nftsong.model.NftChainMetadata.Ethereum")
data class EthereumChainMetadata(
    val contractAddress: String,
    val tokenType: String,
    val tokenId: String,
) : ChainMetadata()
