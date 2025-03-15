package io.newm.shared.internal.services.network

import io.newm.shared.internal.EarningsAPI
import io.newm.shared.internal.api.models.EarningsResponse

internal class EarningsNetworkService(
    private val api: EarningsAPI
) {
    suspend fun getEarningsForWalletId(walletAddress: String, humanVerificationCode: String): EarningsResponse {
         return api.getEarningsForWalletId(walletAddress, humanVerificationCode)
    }

}