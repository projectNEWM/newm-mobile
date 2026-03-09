package io.newm.shared.commonPublic.models

import kotlinx.serialization.Serializable

@Serializable
data class WalletConnection(
    val id: String,
    val createdAt: String,
    val address: String,
    val chain: ChainType,
    val name: String,
)
