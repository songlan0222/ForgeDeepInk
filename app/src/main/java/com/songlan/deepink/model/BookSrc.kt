package com.songlan.deepink.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.songlan.deepink.enum.Comments

@Entity
class BookSrc(
    var bookSrcName: String,
    var comments: Comments = Comments.TEN,
    var useTimes: Int = 0,
    var webSet: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var bookSrcId: Long = 0


}




