package com.songlan.deepink.model

import androidx.room.Entity

@Entity
data class SettingItem(
    val itemNum: Int,
    val imageId: Int,
    val itemName: String,
) {
}