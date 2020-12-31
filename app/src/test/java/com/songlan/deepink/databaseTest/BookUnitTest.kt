package com.songlan.deepink.databaseTest

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.songlan.deepink.AppDatabase
import com.songlan.deepink.R
import com.songlan.deepink.dao.BookDao
import com.songlan.deepink.model.Book
import com.songlan.deepink.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog
import kotlin.jvm.Throws

@RunWith(
    AndroidJUnit4::class
)
class BookUnitTest {

    private var bookDao: BookDao? = null
    private var appDataBase: AppDatabase? = null

    private fun initRxJava2() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    private fun <T> doWithRxJava(t: T): Observable<T> {
        return Observable.create<T> { it.onNext(t) }
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        appDataBase = AppDatabase.getDatabase(context)
        bookDao = appDataBase?.bookDao()
        ShadowLog.stream = System.out
        initRxJava2()
    }


    @Test
    fun bookTest_Insert() {
        val book1 = Book(
            R.drawable.ic_book_default, "斗罗大陆", "唐家三少", "唐门外门弟子唐三"
        )
        doWithRxJava(bookDao?.insertBook(book1)).subscribe { bookId ->
            LogUtils.d("UnitTest", "BookId = $bookId")
        }
    }

    @Test
    fun bookTest_LoadAllBooks() {
        LogUtils.d("UnitTest", "Start Unit Testing")
        doWithRxJava(bookDao?.loadAllBooks()).subscribe() { bookList ->
            bookList?.forEach { book ->
                LogUtils.e(
                    "UnitTest",
                    "书籍编号：${book.bookId}, \n" +
                            "书籍名称：${book.bookName}, \n" +
                            "书籍作者：${book.bookAuthor}, \n" +
                            "书架编号： ${book.bookshelfId}"
                )
            }
        }
    }

    @Test
    fun bookTest_LoadBookWithBookshelfId() {
        doWithRxJava(bookDao?.loadBookWithBookshelfId(1)).subscribe { bookList ->
            bookList?.forEach { book ->
                com.songlan.deepink.utils.LogUtils.d(
                    "UnitTest",
                    "书籍编号：${book.bookId}, \n" +
                            "书籍名称：${book.bookName}, \n" +
                            "书籍作者：${book.bookAuthor}, \n" +
                            "书架编号： ${book.bookshelfId}"
                )
            }
        }
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        appDataBase?.close()
    }
}
