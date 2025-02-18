package io.newm.shared.internal.implementations

import io.newm.shared.internal.repositories.EarningsRepository
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.GetInvestmentPortfolioDataUseCase
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
        )?.earnings?.forEach { totalUnclaimed += it.amount }

        // Ensure division by zero is handled, defaulting to 0 if totalUnclaimed is 0
        return if (totalUnclaimed == 0L) 0L else (totalUnclaimed / 1_000_000L)
    }
}
