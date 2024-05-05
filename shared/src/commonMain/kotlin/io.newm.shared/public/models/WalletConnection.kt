package io.newm.shared.public.models

import kotlinx.serialization.Serializable

@Serializable
data class WalletConnection(
    val id: String,
    val createdAt: String,
    val stakeAddress: String
)
