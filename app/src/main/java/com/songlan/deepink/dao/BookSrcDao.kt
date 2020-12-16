package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.BookSrc

@Dao
interface BookSrcDao {

    @Insert
    fun insertBookSrc(bookSrc: BookSrc)

    @Update
    fun updateBookSrc(bookSrc: BookSrc)

    @Delete
    fun deleteBookSrc(bookSrc: BookSrc)

    @Query("delete from BookSrc where bookSrcId = :bookSrcId")
    fun deleteBookSrcWithId(bookSrcId: Long)
}