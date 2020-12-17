package com.songlan.deepink.model

import androidx.room.TypeConverter
import java.sql.Date

class BookDataConverter {
    @TypeConverter
    fun revertDate(value: Long): Date = Date(value)

    @TypeConverter
    fun convertDate(value: Date): Long = value.time
}