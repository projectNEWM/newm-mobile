package io.newm.shared.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.models.Genre
import io.newm.shared.services.GenresAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface GenresRepository {
    suspend fun getGenres(): List<Genre>
}

internal class GenresRepositoryImpl : KoinComponent, GenresRepository {
    private val service: GenresAPI by inject()

    private val logger = Logger.withTag("NewmKMM-GenreRepo")

    override suspend fun getGenres(): List<Genre> {
        logger.d { "getGenres" }
        return service.getGenres()
    }
}