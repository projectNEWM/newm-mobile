package io.newm.shared.internal.repositories

import io.newm.shared.NewmAppLogger
import io.newm.shared.internal.api.models.EarningsResponse
import io.newm.shared.internal.services.network.EarningsNetworkService

internal class EarningsRepository(
    private val networkService: EarningsNetworkService,
    private val logger: NewmAppLogger
) {

    suspend fun getEarningsForWalletId(
        walletAddress: String,
        humanVerificationCode: String
    ): EarningsResponse? {
        return try {
            val reponse =
                networkService.getEarningsForWalletId(walletAddress, humanVerificationCode)
            logger.info("EarningsRepository", "Earnings fetched from network: $reponse")
            //TODO: Serialize the response and return it
            reponse
        } catch (e: Exception) {
            logger.error(
                "EarningsRepository",
                "Error fetching earnings from network ${e.cause}",
                e
            )
            return null
        }
    }


}