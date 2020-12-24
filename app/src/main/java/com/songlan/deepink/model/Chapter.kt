package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chapter(
    var chapterName: String,
    var contentPath: String,
    var bookId: Long
) {

    @PrimaryKey(autoGenerate = true)
    var chapterId: Long = 0
}