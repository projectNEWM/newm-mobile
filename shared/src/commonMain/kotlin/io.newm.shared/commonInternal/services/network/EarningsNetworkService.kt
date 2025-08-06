package io.newm.shared.commonInternal.services.network

import io.newm.shared.commonInternal.EarningsAPI
import io.newm.shared.commonInternal.api.models.EarningsResponse

class EarningsNetworkService(
    private val api: EarningsAPI
) {
    suspend fun getEarningsForWalletId(walletAddress: String, humanVerificationCode: String): EarningsResponse {
         return api.getEarningsForWalletId(walletAddress, humanVerificationCode)
    }

}