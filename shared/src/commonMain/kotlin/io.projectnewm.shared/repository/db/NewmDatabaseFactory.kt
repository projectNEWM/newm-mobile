package io.projectnewm.shared.repository.db

import com.squareup.sqldelight.db.SqlDriver
import io.projectnewm.shared.example.ExampleDomainModel
import ioprojectnewmsharedrepositorydb.ExampleEntity

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