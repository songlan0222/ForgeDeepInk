package com.songlan.deepink.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.songlan.deepink.AppDatabase
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

object DatabaseRepository {

    private val bookDao = AppDatabase.getDatabase(context).bookDao()
    private val bookshelfDao = AppDatabase.getDatabase(context).bookshelfDao()

    // 书籍管理方法
    fun insertBook(book: Book) = fire(Dispatchers.IO) {
        val bookId = bookDao?.insertBook(book)
        Result.success(bookId)
    }

    fun loadBookList(bookshelfId: Long) = fire(Dispatchers.IO) {
        val bookList = bookDao.loadBookWithBookshelfId(bookshelfId)
        Result.success(bookList)
    }

    fun loadAllBooks() = fire(Dispatchers.IO) {
        val bookList = bookDao.loadAllBooks()
        Result.success(bookList)
    }


    // 书架管理方法
    fun insertBookshelf(bookshelf: Bookshelf) = fire(Dispatchers.IO) {
        val bookshelfId = bookshelfDao.insertBookshelf(bookshelf)
        Result.success(bookshelfId)
    }

    fun updateBookshelf(bookshelf: Bookshelf) = fire(Dispatchers.IO) {
        bookshelfDao.updateBookshelf(bookshelf)
        Result.success(Any())
    }

    fun loadBookshelfList() = fire(Dispatchers.IO) {
        val bookshelfList = bookshelfDao.loadAllBookshelf()
        Result.success(bookshelfList)
    }

    // 该方法逻辑有问题，待修改
    /*fun loadBookshelfWithId(query: Long) = fire(Dispatchers.IO) {
        // bookshelfDao.deleteAllBookshelf()
        val bookshelf = bookshelfDao.loadBookshelf(query)
        // 如果数据库中没有被选中bookshelf，说明此事bookshelf为空；此时，则创建bookshelf，并添加到数据库
        if (bookshelf != null) {
            // bookDao.insertBook(Book(R.drawable.ic_book_default, "大王饶命", bookshelfId = 1))
            Result.success(bookshelf)
        } else {
            val initBookshelf = Bookshelf("默认", true)
            bookshelfDao.insertBookshelf(initBookshelf)
            Result.success(initBookshelf)
        }
    }*/

    fun loadBookshelf(bookshelfId: Long) = fire(Dispatchers.IO) {
        val bookshelf = bookshelfDao.loadBookshelf(bookshelfId)
        Result.success(bookshelf)
    }

    fun deleteBookshelf(bookshelfId: Long) = fire(Dispatchers.IO) {
        val deleteBookshelf = bookshelfDao.loadBookshelf(bookshelfId)
        bookshelfDao.deleteBookshelf(deleteBookshelf)
        Result.success(Any())
    }

    fun getFirstChooseBookshelf() = fire(Dispatchers.IO) {
        val bookshelfId = bookshelfDao.getFirstChooseBookshelf()
        Result.success(bookshelfId)
    }

    // 对获取liveData进行简化
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            var result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}