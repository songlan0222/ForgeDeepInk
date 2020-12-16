package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Bookshelf

@Dao
interface BookshelfDao {
    @Insert
    fun insertBookshelf(bookshelf: Bookshelf): Long

    @Update
    fun updateBookshelf(bookshelf: Bookshelf)

    @Query("select * from bookshelf where bookshelfId = :bookshelfId")
    fun loadBookshelf(bookshelfId: Long): Bookshelf

    @Query("select * from Bookshelf")
    fun loadAllBookshelf(): List<Bookshelf>

    @Delete
    fun deleteBookshelf(bookshelf: Bookshelf)

    @Query("delete from Bookshelf where 1= 1")
    fun deleteAllBookshelf(): Int


}