package com.projectnewm.util

data class DataState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
    // TODO: Add proper error state info.
) {

    companion object {

        fun <T> error(): DataState<T> {
            return DataState(data = null, isError = true)
        }

        fun <T> data(data: T? = null): DataState<T> {
            return DataState(data = data)
        }

        fun <T> loading() = DataState<T>(isLoading = true)
    }
}