package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChapterContent(
    var content: String,
    var chapterId: Long
) {

    @PrimaryKey(autoGenerate = true)
    var chapterContentId: Long = 0
}