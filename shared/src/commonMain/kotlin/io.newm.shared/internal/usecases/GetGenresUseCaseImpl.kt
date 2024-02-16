package io.newm.shared.internal.usecases

import io.newm.shared.internal.repositories.GenresRepository
import io.newm.shared.internal.usecases.utilities.mapErrorsSuspend
import io.newm.shared.public.models.Genre
import io.newm.shared.public.models.error.KMMException
import io.newm.shared.public.usecases.GetGenresUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class GetGenresUseCaseImpl(private val repository: GenresRepository) : GetGenresUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getGenres(): List<Genre> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.getGenres()
        }
    }
}