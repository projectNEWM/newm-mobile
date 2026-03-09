package io.newm.shared.di

import io.newm.shared.commonPublic.models.CardanoChainMetadata
import io.newm.shared.commonPublic.models.ChainMetadata
import io.newm.shared.commonPublic.models.EthereumChainMetadata
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val jsonModule =
    SerializersModule {
        polymorphic(ChainMetadata::class) {
            subclass(CardanoChainMetadata::class)
            subclass(EthereumChainMetadata::class)
        }
    }

fun createJson() =
    Json {
        serializersModule = jsonModule
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        // Note: Using default classDiscriminator ("type") since chainType is a separate field
        // at the NFTTrack level, not the polymorphic discriminator inside chainMetadata
    }
