package io.newm.shared.playlist.usecases

import io.newm.shared.login.repository.KMMException
import io.newm.shared.playlist.repository.GenresRepository
import kotlin.coroutines.cancellation.CancellationException

interface GetGenresUseCase {
    @Throws(KMMException::class, CancellationException::class)
    suspend fun getGenres(): List<String>
}

internal class GetGenresUseCaseImpl(private val repository: GenresRepository) : GetGenresUseCase {

    @Throws(KMMException::class, CancellationException::class)
    override suspend fun getGenres(): List<String> {
        return repository.getGenres()
    }
}