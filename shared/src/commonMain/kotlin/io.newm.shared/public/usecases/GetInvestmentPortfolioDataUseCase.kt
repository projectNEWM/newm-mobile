package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException

interface GetInvestmentPortfolioDataUseCase {

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getInvestmentPortfolio(
        walletAddress: String,
        humanVerificationCode: String
    ): Long
}