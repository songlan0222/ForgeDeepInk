package com.songlan.deepink.dao.app

import android.content.ClipData
import androidx.room.*
import com.songlan.deepink.model.app.ReadPageMenuItem

@Dao
interface ReadPageMenuItemDao {

    @Insert
    fun insertItem(item: ReadPageMenuItem): Long

    @Insert
    fun insertItems(items: List<ReadPageMenuItem>): List<Long>

    @Update
    fun updateItem(item: ReadPageMenuItem)

    @Delete
    fun deleteItem(item: ReadPageMenuItem)

    @Query("select * from ReadPageMenuItem where itemId = :id")
    fun loadItemWithId(id: Long): ReadPageMenuItem

    @Query("select * from ReadPageMenuItem where 1 = 1")
    fun loadAll(): List<ReadPageMenuItem>
}