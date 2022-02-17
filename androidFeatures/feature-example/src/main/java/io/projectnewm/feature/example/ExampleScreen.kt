package io.projectnewm.feature.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ExampleScreen(
    viewModel: ExampleViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Example Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(CenterHorizontally),
            fontSize = 25.sp
        )

        if (viewState.titles.isNotEmpty()) {
            Text(
                "Titles: ${viewState.titles}",
                color = Color.White,
                modifier = Modifier.align(CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
        if (viewState.isLoading) {
            Text(
                "Loading... ",
                color = Color.White,
                modifier = Modifier.align(CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}