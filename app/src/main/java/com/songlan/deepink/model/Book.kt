package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.sql.Date

@Entity
@TypeConverters(BookDataConverter::class)
data class Book(
    var bookImage: Int,
    var bookName: String,
    var bookAuthor: String = "佚名",
    var bookSimpleIntro: String = "",
    var lastUpdate: Date = Date(0),
    var readProgress: Float = 0F,
    var readTime: Float = 0F,
    var bookshelfId: Long = 1,
) {
    @PrimaryKey(autoGenerate = true)
    var bookId: Long = 0
}

class BookDataConverter {
    @TypeConverter
    fun revertDate(value: Long): Date = Date(value)

    @TypeConverter
    fun convertDate(value: Date): Long = value.time
}