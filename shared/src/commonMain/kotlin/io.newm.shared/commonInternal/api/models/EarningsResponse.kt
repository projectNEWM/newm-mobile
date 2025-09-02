package io.newm.shared.commonInternal.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EarningsResponse(
    @SerialName("totalClaimed")
    val totalClaimed: Long,
    @SerialName("earnings")
    val earnings: List<Earning>,
    @SerialName("amountCborHex")
    val amountCborHex: String
)

@Serializable
data class Earning(
    @SerialName("id")
    val id: String,
    @SerialName("songId")
    val songId: String? = null,
    @SerialName("stakeAddress")
    val stakeAddress: String,
    @SerialName("amount")
    val amount: Long,
    @SerialName("memo")
    val memo: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String? = null,
    @SerialName("claimed")
    val claimed: Boolean? = null,
    @SerialName("claimedAt")
    val claimedAt: String? = null,
    @SerialName("claimOrderId")
    val claimOrderId: String? = null,
    @SerialName("createdAt")
    val createdAt: String
)