package com.songlan.deepink.ui.read

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.repository.DatabaseRepository

class ReadBookActivityVM : ViewModel() {

    // 获取打开图书的id值
    var bookId = -1L

    private val pBookLiveData = MutableLiveData<Long>()
    lateinit var book: Book
    val bookLiveData = Transformations.switchMap(pBookLiveData) { bookId ->
        DatabaseRepository.loadBookWithBookId(bookId)
    }

    fun loadBook(bookId: Long) {
        pBookLiveData.value = bookId
    }

    private val pLoadChapterTitleWithBookId = MutableLiveData<Long>()
    val chapterTitles = ArrayList<Chapter>()
    val loadChaptersWithBookId =
        Transformations.switchMap(pLoadChapterTitleWithBookId) { bookId ->
            DatabaseRepository.loadChaptersWithBookId(bookId)
        }

    fun loadChapterTitleWithBookId(bookId: Long) {
        pLoadChapterTitleWithBookId.value = bookId
    }


    private val pLoadChapterWithChapterId = MutableLiveData<Long>()
    lateinit var chapter: Chapter
    val loadChapterWithChapterIdLiveData =
        Transformations.switchMap(pLoadChapterWithChapterId) { chapterId ->
            DatabaseRepository.loadChapterWithChapterId(chapterId)
        }


    fun loadChapterWithChapterId(chapterId: Long) {
        pLoadChapterWithChapterId.value = chapterId
    }

    companion object {
        // 小说页默认显示中间页面
        const val DEFAULT_ITEM_ID = 1
    }
}