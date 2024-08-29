package io.newm.shared.internal.repositories

import io.newm.shared.public.models.Genre
import io.newm.shared.internal.api.GenresAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class GenresRepository : KoinComponent {
    private val service: GenresAPI by inject()

    suspend fun getGenres(): List<Genre> {
        return service.getGenres()
    }
}