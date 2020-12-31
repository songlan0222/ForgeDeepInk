package com.songlan.deepink.databaseTest

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.songlan.deepink.AppDatabase
import com.songlan.deepink.dao.BookshelfDao
import com.songlan.deepink.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog
import kotlin.jvm.Throws

@RunWith(
    AndroidJUnit4::class
)
class BookshelfUnitTest {

    private var bookshelfDao: BookshelfDao? = null

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
        bookshelfDao = AppDatabase.getDatabase(context).bookshelfDao()
        ShadowLog.stream = System.out
        initRxJava2()
    }

    @Test
    fun loadTest_AllBookshelfs() {
        LogUtils.d(
            "UnitTest",
            "loadTest_AllBookshelfs"
        )
        doWithRxJava(bookshelfDao?.loadAllBookshelf()).subscribe { bookshelfList ->
            bookshelfList?.forEach { bookshelf ->
                LogUtils.d(
                    "UnitTest",
                    "name:${bookshelf.bookshelfName} —— id:${bookshelf.bookshelfId}"
                )
            }

        }
    }

}