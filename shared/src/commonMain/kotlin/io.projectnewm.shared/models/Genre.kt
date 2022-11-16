package io.projectnewm.shared.models

final enum class Genre {
    ROCK, CLASSICAL, RAP, FOLK, COUNTRY;

    companion object {
        val allCases: List<Genre> = listOf<Genre>(ROCK, CLASSICAL, RAP, FOLK, COUNTRY)
    }

    //TODO: Add localization
    val title : String
        get() = when (this) {
            FOLK -> "Folk"
            CLASSICAL -> "Classical"
            COUNTRY -> "Country"
            RAP -> "Rap"
            ROCK -> "Rock"
        }
}