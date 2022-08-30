package io.projectnewm.shared.example

import io.projectnewm.shared.util.CommonFlow
import io.projectnewm.shared.util.DataState
import io.projectnewm.shared.util.asCommonFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

internal class ExampleUseCase constructor(private val exampleRepository: ExampleRepository) {

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