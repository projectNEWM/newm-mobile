package io.projectnewm.shared.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ExampleResponse(

    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String
)

internal fun List<ExampleResponse>.toExampleDomainModelList(): List<ExampleDomainModel> {
    return map {
        ExampleDomainModel(
            id = it.id,
            title = it.title
        )
    }
}