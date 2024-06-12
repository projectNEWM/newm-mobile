package io.newm.shared.internal.repositories

import co.touchlab.kermit.Logger
import io.newm.shared.public.models.Genre
import io.newm.shared.internal.api.GenresAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class GenresRepository : KoinComponent {
    private val service: GenresAPI by inject()

    private val logger = Logger.withTag("NewmKMM-GenreRepo")

    suspend fun getGenres(): List<Genre> {
        logger.d { "getGenres" }
        return service.getGenres()
    }
}