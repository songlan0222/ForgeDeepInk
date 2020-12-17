package com.songlan.deepink.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.songlan.deepink.AppDatabase
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
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

    fun getBookshelfWithChecked() = liveData(Dispatchers.IO) {
        val result = try {
            val bookshelf = bookshelfDao.loadBookshelfWithChecked()
            // 如果数据库中没有被选中bookshelf，说明此事bookshelf为空；此时，则创建bookshelf，并添加到数据库
            if (bookshelf != null)
                Result.success(bookshelf)
            else {
                val initBookshelf = Bookshelf("默认", true)
                bookshelfDao.insertBookshelf(initBookshelf)
                Result.success(initBookshelf)
            }
        } catch (e: Exception) {
            Result.failure<Bookshelf>(e)
        }
        emit(result)
    }

    fun getBookshelfWithId(query: Long) = liveData(Dispatchers.IO) {
        val result = try {
            val bookshelf = bookshelfDao.loadBookshelf(query)
            Result.success(bookshelf)
        } catch (e: Exception) {
            Result.failure<Bookshelf>(e)
        }
        emit(result)
    }

    fun getBookshelfList() = liveData(Dispatchers.IO) {
        val result = try {
            val bookshelfList = bookshelfDao.loadAllBookshelf()
            Result.success(bookshelfList)
        } catch (e: Exception) {
            Result.failure<List<Bookshelf>>(e)
        }
        emit(result)

    }
}