package io.projectnewm.shared.example

internal data class ExampleViewState(
    val isLoading: Boolean = false,
    val titles: List<String> = emptyList(),
    val errorMessage: String? = null
)