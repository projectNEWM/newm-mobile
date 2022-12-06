package io.newm.screens.home.categories

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.theme.raleway

@Composable
fun CategoryTabs(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = Color.Black,
        modifier = modifier,
        indicator = { },
        divider = { }
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) },
            ) {
                CategoryOption(
                    text = category.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun CategoryOption(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontFamily = raleway,
        fontWeight = when {
            selected -> FontWeight.Bold
            else -> FontWeight.Medium
        },
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = when {
            selected -> Color.White
            else -> Color.Gray
        }
    )
}
