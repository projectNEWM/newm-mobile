package io.newm.shared.commonInternal.repositories

import io.newm.shared.commonInternal.api.GenresAPI
import io.newm.shared.commonPublic.models.Genre
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class GenresRepository : KoinComponent {
    private val service: GenresAPI by inject()

    suspend fun getGenres(): List<Genre> = service.getGenres()
}
