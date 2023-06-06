package io.newm.shared.usecases

import io.newm.shared.login.repository.KMMException
import io.newm.shared.models.Genre
import io.newm.shared.repositories.GenresRepository
import kotlin.coroutines.cancellation.CancellationException

interface GetGenresUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun getGenres(): List<Genre>
}

internal class GetGenresUseCaseImpl(private val repository: GenresRepository) : GetGenresUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getGenres(): List<Genre> {
        return repository.getGenres()
    }
}