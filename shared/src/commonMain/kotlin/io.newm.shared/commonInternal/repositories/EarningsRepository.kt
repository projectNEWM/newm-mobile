package io.newm.shared.commonInternal.repositories

import io.newm.shared.NewmAppLogger
import io.newm.shared.commonInternal.api.models.EarningsResponse
import io.newm.shared.commonInternal.services.network.EarningsNetworkService

internal class EarningsRepository(
    private val networkService: EarningsNetworkService,
    private val logger: NewmAppLogger,
) {
    suspend fun getEarningsForWalletId(
        walletAddress: String,
        humanVerificationCode: String,
    ): EarningsResponse? {
        return try {
            val response = networkService.getEarningsForWalletId(walletAddress, humanVerificationCode)
            logger.info("EarningsRepository", "Earnings fetched from network: $response")
            // TODO: Serialize the response and return it
            response
        } catch (e: Exception) {
            logger.error("EarningsRepository", "Error fetching earnings from network ${e.cause}", e)
            return null
        }
    }
}
