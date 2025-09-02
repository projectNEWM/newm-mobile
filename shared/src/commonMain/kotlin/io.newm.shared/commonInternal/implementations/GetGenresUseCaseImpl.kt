package io.newm.shared.commonInternal.implementations

import io.newm.shared.commonInternal.repositories.GenresRepository
import io.newm.shared.commonInternal.implementations.utilities.mapErrorsSuspend
import io.newm.shared.commonPublic.models.Genre
import io.newm.shared.commonPublic.models.error.KMMException
import io.newm.shared.commonPublic.usecases.GetGenresUseCase
import kotlin.coroutines.cancellation.CancellationException

internal class GetGenresUseCaseImpl(private val repository: GenresRepository) : GetGenresUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getGenres(): List<Genre> {
        return mapErrorsSuspend {
            return@mapErrorsSuspend repository.getGenres()
        }
    }
}