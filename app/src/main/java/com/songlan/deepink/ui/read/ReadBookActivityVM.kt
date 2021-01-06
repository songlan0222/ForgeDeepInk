package com.songlan.deepink.ui.read

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.repository.DatabaseRepository

class ReadBookActivityVM : ViewModel() {

    // 根据bookId获取书籍内容
    private val pBookLiveData = MutableLiveData<Long>()
    lateinit var book: Book
    val bookLiveData = Transformations.switchMap(pBookLiveData) { bookId ->
        DatabaseRepository.loadBookWithBookId(bookId)
    }

    fun loadBook(bookId: Long) {
        pBookLiveData.value = bookId
    }

    // 根据bookId获取书籍章节信息列表
    private val pLoadChaptersWithBookIdLiveData = MutableLiveData<Long>()
    val loadChaptersWithBookId = ArrayList<Chapter>()
    val loadChaptersWithBookIdLiveData =
        Transformations.switchMap(pLoadChaptersWithBookIdLiveData) { bookId ->
            DatabaseRepository.loadChaptersWithBookId(bookId)
        }

    fun loadChaptersWithBookId(bookId: Long) {
        pLoadChaptersWithBookIdLiveData.value = bookId
    }

    // 根据chapterId获取章节内容
    private val pLoadChapterWithChapterId = MutableLiveData<Long>()
    lateinit var chapter: Chapter
    val loadChapterWithChapterIdLiveData =
        Transformations.switchMap(pLoadChapterWithChapterId) { chapterId ->
            DatabaseRepository.loadChapterWithChapterId(chapterId)
        }

    fun loadChapterWithChapterId(chapterId: Long) {
        pLoadChapterWithChapterId.value = chapterId
    }

    // 根据章节id获取的章节
    private val pLoadReadingChapterLiveData = MutableLiveData<Long>()
    lateinit var readingChapter: Chapter
    val loadReadingChapterLiveData =
        Transformations.switchMap(pLoadReadingChapterLiveData) { chapterId ->
            DatabaseRepository.loadChapterWithChapterId(chapterId)
        }
    fun loadReadingChapter(chapterId: Long) {
        pLoadReadingChapterLiveData.value = chapterId
    }


    // 更新书籍信息
    private val pUpdateBookLiveData = MutableLiveData<Book>()
    val updateBookLiveData = Transformations.switchMap(pUpdateBookLiveData) { book ->
        DatabaseRepository.updateBook(book)
    }

    fun updateBook(book: Book) {
        pUpdateBookLiveData.value = book
    }

    private val pGetFirstChapterWithBookIdLiveData = MutableLiveData<Long>()
    val getFirstChapterWithBookIdLiveData =
        Transformations.switchMap(pGetFirstChapterWithBookIdLiveData) { bookId ->
            DatabaseRepository.getFirstChapterWithBookId(bookId)
        }

    fun getFirstChapterWithBookId(bookId: Long) {
        pGetFirstChapterWithBookIdLiveData.value = bookId
    }

    companion object {
        // 小说页默认显示中间页面
        const val DEFAULT_ITEM_ID = 1
    }

    //private val pSelectedChapter =
}