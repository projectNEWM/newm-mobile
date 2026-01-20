package io.newm.shared.commonPublic.models

import kotlinx.serialization.Serializable

@Serializable
data class WalletConnection(
    val id: String,
    // val localName: String, do we want to add the custom name here?
    // val blockchainType: String, how we choose the wallet icon
    val createdAt: String,
    val stakeAddress: String,
)
