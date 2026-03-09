package io.newm.shared.commonInternal.api.models

import io.newm.shared.commonPublic.models.CardanoChainMetadata
import io.newm.shared.commonPublic.models.ChainType
import io.newm.shared.commonPublic.models.EthereumChainMetadata
import io.newm.shared.commonPublic.models.NFTAllocation
import io.newm.shared.commonPublic.models.NFTTrack
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
internal data class NFTSongResponse(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("audioUrl") val audioUrl: String,
    @SerialName("duration") val duration: Long,
    @SerialName("artists") val artists: List<String> = emptyList(),
    @SerialName("genres") val genres: List<String> = emptyList(),
    @SerialName("moods") val moods: List<String> = emptyList(),
    @SerialName("allocations") val allocations: List<NFTAllocationResponse> = emptyList(),
    @SerialName("amount") val amount: Long,
    @SerialName("chainMetadata") val chainMetadata: NFTChainMetadataResponse,
)

internal fun NFTSongResponse.toDomainOrNull(): NFTTrack? =
    when (val metadata = chainMetadata) {
        is CardanoNFTChainMetadataResponse -> {
            NFTTrack(
                id = id,
                title = title,
                imageUrl = imageUrl,
                audioUrl = audioUrl,
                duration = duration,
                artists = artists,
                genres = genres,
                moods = moods,
                amount = amount,
                chainType = ChainType.Cardano,
                chainMetadata =
                    CardanoChainMetadata(
                        fingerprint = metadata.fingerprint,
                        policyId = metadata.policyId,
                        assetName = metadata.assetName,
                        isStreamToken = metadata.isStreamToken,
                    ),
                allocations = allocations.map(NFTAllocationResponse::toDomain),
            )
        }

        is EthereumNFTChainMetadataResponse -> {
            NFTTrack(
                id = id,
                title = title,
                imageUrl = imageUrl,
                audioUrl = audioUrl,
                duration = duration,
                artists = artists,
                genres = genres,
                moods = moods,
                amount = amount,
                chainType = ChainType.Ethereum,
                chainMetadata =
                    EthereumChainMetadata(
                        contractAddress = metadata.contractAddress,
                        tokenType = metadata.tokenType,
                        tokenId = metadata.tokenId,
                    ),
                allocations = allocations.map(NFTAllocationResponse::toDomain),
            )
        }

        is UnknownNFTChainMetadataResponse -> {
            null
        }
    }

@Serializable
internal data class NFTAllocationResponse(
    @SerialName("id") val id: String,
    @SerialName("amount") val amount: Long,
)

internal fun NFTAllocationResponse.toDomain(): NFTAllocation = NFTAllocation(id = id, amount = amount, walletId = id)

@Serializable(with = NFTChainMetadataResponseSerializer::class)
internal sealed interface NFTChainMetadataResponse {
    val chain: String
}

@Serializable
internal data class CardanoNFTChainMetadataResponse(
    @SerialName("chain") override val chain: String,
    @SerialName("fingerprint") val fingerprint: String,
    @SerialName("policyId") val policyId: String,
    @SerialName("assetName") val assetName: String,
    @SerialName("isStreamToken") val isStreamToken: Boolean,
) : NFTChainMetadataResponse

@Serializable
internal data class EthereumNFTChainMetadataResponse(
    @SerialName("chain") override val chain: String,
    @SerialName("contractAddress") val contractAddress: String,
    @SerialName("tokenType") val tokenType: String,
    @SerialName("tokenId") val tokenId: String,
) : NFTChainMetadataResponse

@Serializable
internal data class UnknownNFTChainMetadataResponse(
    @SerialName("chain") override val chain: String = ChainType.Unknown.serialName,
) : NFTChainMetadataResponse

internal object NFTChainMetadataResponseSerializer :
    JsonContentPolymorphicSerializer<NFTChainMetadataResponse>(NFTChainMetadataResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<NFTChainMetadataResponse> =
        when (
            element.jsonObject["chain"]
                ?.jsonPrimitive
                ?.contentOrNull
                ?.lowercase()
        ) {
            "cardano" -> CardanoNFTChainMetadataResponse.serializer()
            "ethereum" -> EthereumNFTChainMetadataResponse.serializer()
            else -> UnknownNFTChainMetadataResponse.serializer()
        }
}
