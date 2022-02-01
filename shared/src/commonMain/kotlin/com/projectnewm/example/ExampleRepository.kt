package com.projectnewm.example

import com.projectnewm.repository.db.ExampleDao
import com.projectnewm.util.Logger

class ExampleRepository(
    private val exampleService: ExampleService,
    private val exampleDao: ExampleDao
) {

    private val logger = Logger("ExampleRepository")

    suspend fun loadExampleData(): List<ExampleDomainModel> {

        if (isCacheTtlValid()) {
            val list = exampleDao.getAll()

            logger.log("List from cache: $list")
            // Normally would short circuit here and return but just going to log for demo purposes.
        }

        val response = exampleService.get()

        exampleDao.insert(response)

        return response
    }

    private fun isCacheTtlValid() = true
}