package com.projectnewm.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.projectnewm.screens.home.categories.CategoryTabs
import com.projectnewm.screens.home.categories.MusicalCategoriesViewModel

internal const val TAG_HOME_SCREEN = "TAG_HOME_SCREEN"

@Composable
fun HomeScreen(onShowDetails: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .testTag(TAG_HOME_SCREEN)
    ) {
        val viewModel: MusicalCategoriesViewModel = viewModel()
        val viewState by viewModel.state.collectAsState()
        val selectedCategory = viewState.selectedCategory

        if (viewState.categories.isNotEmpty() && selectedCategory != null) {
            CategoryTabs(
                categories = viewState.categories,
                selectedCategory = selectedCategory,
                onCategorySelected = viewModel::onCategorySelected,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
