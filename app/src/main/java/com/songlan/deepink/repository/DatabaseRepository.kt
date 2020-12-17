package com.songlan.deepink.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.songlan.deepink.AppDatabase
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.model.Book
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import kotlin.concurrent.thread

object DatabaseRepository {

    private val bookDao = AppDatabase.getDatabase(context).bookDao()
    private val bookshelfDao = AppDatabase.getDatabase(context).bookshelfDao()

    fun getBookList(bookshelfId: Long) = liveData(Dispatchers.IO) {
        val result = try {
            val bookList = bookDao.loadBookWithBookshelfId(bookshelfId)
            Result.success(bookList)
        } catch (e: Exception) {
            Result.failure<List<Book>>(e)
        }
        emit(result)
    }
}