package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Bookshelf

@Dao
interface BookshelfDao {
    @Insert
    suspend fun insertBookshelf(bookshelf: Bookshelf): Long

    @Update
    suspend fun updateBookshelf(bookshelf: Bookshelf)

    @Query("select * from Bookshelf where bookshelfId = :bookshelfId")
    suspend fun loadBookshelf(bookshelfId: Long): Bookshelf

    @Query("select * from Bookshelf where 1 = 1")
    suspend fun loadAllBookshelf(): List<Bookshelf>

    @Query("select * from Bookshelf where isFirstChoose = 1")
    suspend fun loadBookshelfWithChecked(): Bookshelf

    @Delete
    suspend fun deleteBookshelf(bookshelf: Bookshelf)

    @Query("delete from Bookshelf where 1= 1")
    suspend fun deleteAllBookshelf(): Int

    @Query("select bookshelfId from Bookshelf where isFirstChoose = 1")
    suspend fun getFirstChooseBookshelf(): Long

    @Query("select * from bookshelf where bookshelfId = 1")
    suspend fun getDefaultBookshelf() : Bookshelf


}