package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Book(
    var bookImage: Int,
    var bookName: String,
    var bookAuthor: String,
    var bookSimpleIntro: String,
    var lastUpadate: Date,
    var readProgress: Float,
    var readTime: Float,
    var bookshelfId: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var bookId: Long = 0
}