package com.songlan.deepink.model.app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReadPageMenuItem(
    var itemImageId: Int,
    var itemName: String,
    var itemSelected: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0
}