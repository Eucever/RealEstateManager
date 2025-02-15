package com.example.realestatemanager.data.converter

import androidx.room.TypeConverter

class ListConverter {

    companion object {
        private const val SEPARATOR = ","
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun fromString(data: String?): List<String> {
        return data?.split(SEPARATOR) ?: emptyList()
    }

    @TypeConverter
    fun fromListLong(list: List<Long>?): String? {
        return list?.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun fromStringToLong(data: String?): List<Long> {
        return data?.split(SEPARATOR)?.map { it.toLong() } ?: emptyList()
    }


}
