package io.projectnewm.feature.example

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.projectnewm.shared.example.ExampleUseCase
import io.projectnewm.shared.example.ExampleViewState

class ExampleViewModel constructor(
    private val exampleUseCase: ExampleUseCase
) : ViewModel() {

    val viewState: MutableState<ExampleViewState> = mutableStateOf(ExampleViewState())

    init {
        loadExampleData()
    }

    private fun loadExampleData() {
        exampleUseCase.execute().collectCommon(viewModelScope) { dataState ->
            viewState.value = viewState.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { data ->
                viewState.value =
                    viewState.value.copy(isLoading = false, titles = data.map { it.title })
            }

            if (dataState.isError) {
                viewState.value =
                    viewState.value.copy(isLoading = false, errorMessage = "Error")
            }
        }
    }
}