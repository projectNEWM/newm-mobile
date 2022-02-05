package com.projectnewm.screens.home.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicalCategoriesViewModel @Inject constructor() : ViewModel() {
    private val DEFAULT = Category(id = -1, "Empty")
    private val _selectedCategory = MutableStateFlow(DEFAULT)
    private val _state = MutableStateFlow(MusicCategoriesViewState())

    val state: StateFlow<MusicCategoriesViewState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                MockData.MusicCategories.categories().onEach { categories ->
                    if (categories.isNotEmpty() && categories.contains(DEFAULT).not()) {
                        _selectedCategory.value = categories.first()
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