package io.newm.shared.internal.usecases

import io.newm.shared.internal.domainservices.HumanVerificationService
import io.newm.shared.internal.usecases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.SetUpHumanVerificationUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class SetUpHumanVerificationUseCaseImpl(
    private val service: HumanVerificationService
): SetUpHumanVerificationUseCase {
    @Throws(KMMException::class, CancellationException::class)
    override suspend fun invoke() {
        mapErrorsSuspend {
            service.setUp()
        }
    }
}