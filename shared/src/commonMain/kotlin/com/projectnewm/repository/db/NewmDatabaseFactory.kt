package com.projectnewm.repository.db

import com.projectnewm.example.ExampleDomainModel
import com.squareup.sqldelight.db.SqlDriver
import comprojectnewmrepositorydb.ExampleEntity

class NewmDatabaseFactory(
    private val driverFactory: SqlDelightDriverFactory
) {
    fun createDatabase(): NewmDb {
        return NewmDb(driverFactory.createDriver())
    }
}

expect class SqlDelightDriverFactory {
    fun createDriver(): SqlDriver
}

fun ExampleEntity.toExampleDomainModel(): ExampleDomainModel {
    return ExampleDomainModel(
        id.toInt(),
        title
    )
}


fun List<ExampleEntity>.toExampleDomainModelList(): List<ExampleDomainModel> {
    return map { it.toExampleDomainModel() }
}