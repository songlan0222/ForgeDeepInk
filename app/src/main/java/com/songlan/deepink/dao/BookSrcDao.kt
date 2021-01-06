package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.BookSrc

@Dao
interface BookSrcDao {

    @Insert
    suspend fun insertBookSrc(bookSrc: BookSrc)

    @Update
    suspend fun updateBookSrc(bookSrc: BookSrc)

    @Delete
    suspend fun deleteBookSrc(bookSrc: BookSrc)

    @Query("delete from BookSrc where bookSrcId = :bookSrcId")
    suspend fun deleteBookSrcWithId(bookSrcId: Long)
}