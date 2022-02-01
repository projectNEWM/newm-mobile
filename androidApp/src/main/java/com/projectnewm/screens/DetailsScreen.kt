package com.projectnewm.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projectnewm.R
import com.projectnewm.example.ExampleViewModel

@Composable
fun DetailsScreen(
    viewModel: ExampleViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.value
    val scaffoldState = rememberScaffoldState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_500))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Details Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }

    if (viewState.titles.isNotEmpty()) {
        Log.w("DetailsScreen", "Titles: $viewState")
    }
    if (viewState.isLoading) {
        Log.w("DetailsScreen", "isLoading: $viewState")
    }

    viewState.errorMessage?.let { message ->
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(message = message)
            viewModel.clearError()
        }
    }
}