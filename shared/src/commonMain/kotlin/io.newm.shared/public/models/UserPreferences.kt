package io.newm.shared.public.models

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
        val librarySort: SortOption,
        val libraryFilter: FilterOptions
)