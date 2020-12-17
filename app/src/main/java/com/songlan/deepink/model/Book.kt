package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Book(
    var bookImage: Int,
    var bookName: String,
    var bookAuthor: String = "佚名",
    var bookSimpleIntro: String = "",
    var lastUpdate: Date = Date(0),
    var readProgress: Float = 0F,
    var readTime: Float = 0F,
    var bookshelfId: Long = 0,
) {
    @PrimaryKey(autoGenerate = true)
    var bookId: Long = 0
}