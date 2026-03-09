package io.newm.shared.commonPublic.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Represents the blockchain type for wallets and NFTs.
 *
 * Uses a custom serializer to handle string-based serialization while maintaining type safety.
 * Unknown chain types from the backend are captured as [Unknown] to prevent crashes when new chains
 * are added server-side before mobile app updates.
 */
@Serializable(with = ChainTypeSerializer::class)
enum class ChainType(
    val serialName: String,
) {
    Cardano("Cardano"),
    Ethereum("Ethereum"),
    Unknown("Unknown"),
    ;

    companion object {
        /**
         * Converts a string value to a [ChainType]. Returns [Unknown] for unrecognized values
         * instead of throwing.
         */
        fun fromString(value: String): ChainType =
            when {
                value.equals("Cardano", ignoreCase = true) -> Cardano
                value.equals("Ethereum", ignoreCase = true) -> Ethereum
                else -> Unknown
            }
    }
}

internal object ChainTypeSerializer : KSerializer<ChainType> {
    override val descriptor = PrimitiveSerialDescriptor("ChainType", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: ChainType,
    ) {
        encoder.encodeString(value.serialName)
    }

    override fun deserialize(decoder: Decoder): ChainType = ChainType.fromString(decoder.decodeString())
}
