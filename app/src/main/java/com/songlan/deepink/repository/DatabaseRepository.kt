package com.songlan.deepink.repository

import android.content.UriPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.songlan.deepink.AppDatabase
import com.songlan.deepink.MyApplication.Companion.context
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.utils.LogUtil
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

    fun updateBook(book: Book) = fire(Dispatchers.IO) {
        bookDao.updateBook(book)
        Result.success(Any())
    }

    fun loadBooksWithBookshelfId(bookshelfId: Long) = fire(Dispatchers.IO) {
        val bookList = bookDao.loadBookWithBookshelfId(bookshelfId)
        Result.success(bookList)
    }

    fun loadAllBooks() = fire(Dispatchers.IO) {
        val bookList = bookDao.loadAllBooks()
        Result.success(bookList)
    }

    fun deleteBook(book: Book) = fire(Dispatchers.IO) {
        bookDao.deleteBook(book)
        Result.success(Any())
    }

    fun deleteBooksWithBookshelfId(bookshelfId: Long) = fire(Dispatchers.IO) {
        val count = bookDao.deleteBookWithBookshelfId(bookshelfId)
        Result.success(count)
    }

    fun deleteAllBooks() = fire(Dispatchers.IO) {
        val count = bookDao.deleteAllBooks()
        Result.success(count)
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

    fun loadAllBookshelfs() = fire(Dispatchers.IO) {
        val bookshelfList = bookshelfDao.loadAllBookshelf()
        Result.success(bookshelfList)
    }

    fun loadBookshelf(bookshelfId: Long) = fire(Dispatchers.IO) {
        val bookshelf = bookshelfDao.loadBookshelf(bookshelfId)
        Result.success(bookshelf)
    }

    fun deleteBookshelf(bookshelfId: Long) = fire(Dispatchers.IO) {
        val deleteBookshelf = bookshelfDao.loadBookshelf(bookshelfId)
        bookshelfDao.deleteBookshelf(deleteBookshelf)
        Result.success(Any())
    }

    fun deleteAllBookshelfs() = fire(Dispatchers.IO) {
        val count = bookshelfDao.deleteAllBookshelf()
        Result.success(count)
    }

    fun getCheckedBookshelf() = fire(Dispatchers.IO) {
        val bookshelfId = bookshelfDao.getFirstChooseBookshelf()
        Result.success(bookshelfId)
    }

    // 本地授权路径管理方法
    fun loadPersistedUriPermissions() = fire(Dispatchers.IO){
        val contentResolver = context.contentResolver
        contentResolver.persistedUriPermissions.forEach {
            LogUtil.v("MainTest", "/${it.uri.path?.replace("/tree/primary:", "")}")
        }
        Result.success(contentResolver.persistedUriPermissions)
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