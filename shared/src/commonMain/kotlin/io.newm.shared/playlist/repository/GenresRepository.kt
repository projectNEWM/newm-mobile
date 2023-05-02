package io.newm.shared.playlist.repository

import co.touchlab.kermit.Logger
import io.newm.shared.playlist.service.GenresAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface GenresRepository {
    suspend fun getGenres(): List<String>
}

internal class GenresRepositoryImpl : KoinComponent, GenresRepository {
    private val service: GenresAPI by inject()

    private val logger = Logger.withTag("NewmKMM-GenreRepo")

    override suspend fun getGenres(): List<String> {
        logger.d { "getGenres" }
        return service.getGenres()
    }
}