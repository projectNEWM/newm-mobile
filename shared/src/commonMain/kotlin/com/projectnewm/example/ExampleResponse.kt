package com.projectnewm.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExampleResponse(

    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String
)

fun List<ExampleResponse>.toExampleDomainModelList(): List<ExampleDomainModel> {
    return map {
        ExampleDomainModel(
            id = it.id,
            title = it.title
        )
    }
}