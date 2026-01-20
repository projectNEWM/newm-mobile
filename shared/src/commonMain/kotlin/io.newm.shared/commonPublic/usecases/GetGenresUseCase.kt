package io.newm.shared.commonPublic.usecases

import io.newm.shared.commonPublic.models.Genre
import io.newm.shared.commonPublic.models.error.KMMException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.cancellation.CancellationException

interface GetGenresUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun getGenres(): List<Genre>
}

class GetGenresUseCaseProvider : KoinComponent {
    private val getGenresUseCase: GetGenresUseCase by inject()

    fun get(): GetGenresUseCase = this.getGenresUseCase
}
