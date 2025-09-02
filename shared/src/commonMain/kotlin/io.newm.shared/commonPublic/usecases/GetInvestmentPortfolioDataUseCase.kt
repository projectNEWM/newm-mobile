package io.newm.shared.commonPublic.usecases

import io.newm.shared.commonPublic.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException

interface GetInvestmentPortfolioDataUseCase {

    @Throws(KMMException::class, CancellationException::class)
    suspend fun getInvestmentPortfolio(
        walletAddress: String,
        humanVerificationCode: String
    ): Long
}