package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Book

@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(book: Book): Long

    @Update
    suspend fun updateBook(book: Book)

    @Query("select * from Book where bookId = :bookId")
    suspend fun loadBookWithBookId(bookId: Long): Book

    @Query("select * from Book where 1 = 1")
    suspend fun loadAllBooks(): List<Book>

    @Query("select * from Book where bookshelfId = :bookshelfId")
    suspend fun loadBookWithBookshelfId(bookshelfId: Long): List<Book>

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("delete from Book where bookshelfId = :bookshelfId")
    suspend fun deleteBookWithBookshelfId(bookshelfId: Long): Int

    @Query("delete from Book where 1=1")
    suspend fun deleteAllBooks(): Int

}