package io.newm.shared.public.usecases

import io.newm.shared.public.models.Genre
import io.newm.shared.public.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface GetGenresUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun getGenres(): List<Genre>
}

class GetGenresUseCaseProvider : KoinComponent {
    private val getGenresUseCase: GetGenresUseCase by inject()

    fun get(): GetGenresUseCase {
        return this.getGenresUseCase
    }
}

