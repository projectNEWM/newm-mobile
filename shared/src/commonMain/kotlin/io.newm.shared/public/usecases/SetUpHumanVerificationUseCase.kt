package io.newm.shared.public.usecases

import io.newm.shared.public.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

interface SetUpHumanVerificationUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend operator fun invoke()
}

class SetUpHumanVerificationUseCaseProvider: KoinComponent {
    private val setUpHumanVerificationUseCase: SetUpHumanVerificationUseCase by inject()

    fun get(): SetUpHumanVerificationUseCase {
        return this.setUpHumanVerificationUseCase
    }
}