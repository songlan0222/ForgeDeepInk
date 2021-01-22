package com.songlan.deepink.model.data

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
    var regex: String = "^.*/s第(.{1,5})[章回节部集卷].{0,24}",
    /**
     * 书籍所在书架的id
     */
    var bookshelfId: Long = 1,
    /**
     * 正在阅读的章节id
     */
    var readingChapterId: Long = -1L,
    /**
     * 当前章节的起始字符位置
     */
    var startCharIndex : Long = 0L,
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