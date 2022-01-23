package com.projectnewm.screens.home.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MusicalCategoriesViewModel : ViewModel() {
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    private val _state = MutableStateFlow(MusicCategoriesViewState())

    val state: StateFlow<MusicCategoriesViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                MockData.MusicCategories.categories().onEach { categories ->
                    if (categories.isNotEmpty() && _selectedCategory.value == null) {
                        _selectedCategory.value = categories[0]
                    }
                }, _selectedCategory
            ) { categories, selectedCategory ->
                MusicCategoriesViewState(
                    categories = categories,
                    selectedCategory = selectedCategory
                )
            }.collect {
                _state.value = it
            }
        }
    }

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
    }
}

data class MusicCategoriesViewState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null
)

data class Category(
    val id: Long = 0,
    val name: String
)