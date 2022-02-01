package com.projectnewm.example

import com.projectnewm.util.CommonFlow
import com.projectnewm.util.DataState
import com.projectnewm.util.asCommonFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class ExampleUseCase constructor(private val exampleRepository: ExampleRepository) {

    fun execute(): CommonFlow<DataState<List<ExampleDomainModel>>> =
        // Note: This is a cold flow so it won't execute this block until you collect.
        flow {
            try {
                emit(DataState.loading())

                // To simulate slightly longer loading times
                delay(500)
                val response = exampleRepository.loadExampleData()

                emit(DataState.data(response))
            } catch (e: Exception) {
                emit(DataState.error())
            }
        }.asCommonFlow()
}