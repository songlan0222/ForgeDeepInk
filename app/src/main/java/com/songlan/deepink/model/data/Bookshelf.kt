package com.songlan.deepink.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookshelf(
    var bookshelfName: String,
    var isFirstChoose: Boolean = false,
    var layoutWay: Int = 0,
    var infoWay: Int = 0,
    var sortWay: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var bookshelfId: Long = 0
}