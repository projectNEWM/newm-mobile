package com.projectnewm.example

class ExampleRepository(
    private val exampleService: ExampleService
) {

    suspend fun loadExampleData(): List<ExampleDomainModel> {

        // TODO: Check cache here and short circuit network call if necessary.

        val response = exampleService.get()

        // TODO: Insert response in cache here.

        return response
    }
}