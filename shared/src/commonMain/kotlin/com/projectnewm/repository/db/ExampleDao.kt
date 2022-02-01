package com.projectnewm.repository.db

import com.projectnewm.example.ExampleDomainModel
import comprojectnewmrepositorydb.NewmDbQueries

interface ExampleDao {
    fun insert(exampleDomainModel: ExampleDomainModel)

    fun insert(exampleDomainModelList: List<ExampleDomainModel>)

    fun get(id: Int): ExampleDomainModel?

    fun getAll(): List<ExampleDomainModel>
}

class ExampleDaoImpl(
    db: NewmDb
) : ExampleDao {
    private var queries: NewmDbQueries = db.newmDbQueries

    override fun insert(exampleDomainModel: ExampleDomainModel) {
        queries.insertPost(
            id = exampleDomainModel.id.toLong(),
            title = exampleDomainModel.title
        )
    }

    override fun insert(exampleDomainModelList: List<ExampleDomainModel>) {
        for (model in exampleDomainModelList) {
            insert(model)
        }
    }

    override fun getAll(): List<ExampleDomainModel> {
        return queries.selectAll().executeAsList().toExampleDomainModelList()
    }

    override fun get(id: Int): ExampleDomainModel? {
        return queries.getPostById(id = id.toLong()).executeAsOneOrNull()?.toExampleDomainModel()
    }
}