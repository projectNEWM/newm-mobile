package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.repositories.EarningsRepository
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.GetInvestmentPortfolioDataUseCase
import kotlin.coroutines.cancellation.CancellationException


internal class GetInvestmentPortfolioDataUseCaseImpl(
    private val earningsRepository: EarningsRepository
) : GetInvestmentPortfolioDataUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getInvestmentPortfolio(
        walletAddress: String,
        humanVerificationCode: String
    ): Long {
        var totalUnclaimed = 0L
        earningsRepository.getEarningsForWalletId(
            walletAddress,
            humanVerificationCode
        )?.earnings?.forEach {
            // claimed == null or false means the earnings are not claimed
            if (it.claimed != true) {
                totalUnclaimed += it.amount
            }
        }

        // Ensure division by zero is handled, defaulting to 0 if totalUnclaimed is 0
        return if (totalUnclaimed == 0L) 0L else (totalUnclaimed / 1_000_000L)
    }
}
