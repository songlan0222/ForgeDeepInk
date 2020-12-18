package com.songlan.deepink.dao

import androidx.room.*
import com.songlan.deepink.model.Book

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book): Long

    @Update
    fun updateBook(book: Book)

    @Query("select * from Book where bookId = :bookId")
    fun loadBookWithBookId(bookId: Long): Book

    @Query("select * from Book where 1 = 1")
    fun loadAllBooks(): List<Book>

    @Query("select * from Book where bookshelfId = :bookshelfId")
    fun loadBookWithBookshelfId(bookshelfId: Long): List<Book>

    @Delete
    fun deleteBook(book: Book)

    @Query("delete from Book where bookshelfId = :bookshelfId")
    fun deleteBookWithBookshelfId(bookshelfId: Long): Int

    @Query("delete from Book where 1=1")
    fun deleteAllBooks(): Int

}