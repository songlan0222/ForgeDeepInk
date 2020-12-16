package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chapter(
    var chapterNumber: String,
    var chapterName: String,
    var chapterContent: String
) {

    @PrimaryKey(autoGenerate = true)
    var chapterId: Long = 0
}