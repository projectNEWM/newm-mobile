package io.newm.shared.database.sqlite

import androidx.room.TypeConverter

class DataConverters {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        return string.split(",")
    }
}
