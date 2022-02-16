package io.projectnewm.screens.home.categories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object MockData {
    object MusicCategories {
        private val categoryList = listOf(
            Category(1, "Explore"),
            Category(2, "Ambient"),
            Category(3, "Hip-Hop"),
            Category(4, "Alternative"),
            Category(5, "Classical")
        )

        fun categories(): Flow<List<Category>> = flow {
            emit(categoryList)
        }
    }
}