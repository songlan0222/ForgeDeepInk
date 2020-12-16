package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookshelf(
    var bookshelfName: String,
    var isFirstChoose: Boolean,
    var layoutWay: Int,
    var infoWay: Int,
    var sortWay: Int
) {
    @PrimaryKey(autoGenerate = true)
    var bookshelfId: Long = 0
}