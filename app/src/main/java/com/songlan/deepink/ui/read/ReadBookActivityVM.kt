package com.songlan.deepink.ui.read

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.repository.ChapterRepository
import com.songlan.deepink.repository.DatabaseRepository
import java.lang.StringBuilder

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
//    private val pLoadChapterWithChapterId = MutableLiveData<Long>()
//    lateinit var chapter: Chapter
//    val loadChapterWithChapterIdLiveData =
//        Transformations.switchMap(pLoadChapterWithChapterId) { chapterId ->
//            DatabaseRepository.loadChapterWithChapterId(chapterId)
//        }
//
//    fun loadChapterWithChapterId(chapterId: Long) {
//        pLoadChapterWithChapterId.value = chapterId
//    }

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
    private val pUpdateBookWithJumpLiveData = MutableLiveData<Book>()
    val updateBookLiveData = Transformations.switchMap(pUpdateBookLiveData) { book ->
        DatabaseRepository.updateBook(book)
    }

    fun updateBook(book: Book) {
        pUpdateBookLiveData.value = book
    }

    val updateBookWithoutJumpLiveData =
        Transformations.switchMap(pUpdateBookWithJumpLiveData) { book ->
            DatabaseRepository.updateBook(book)
        }

    fun updateBookWithoutJump(book: Book) {
        pUpdateBookWithJumpLiveData.value = book
    }

    private val pGetFirstChapterWithBookIdLiveData = MutableLiveData<Long>()
    val getFirstChapterWithBookIdLiveData =
        Transformations.switchMap(pGetFirstChapterWithBookIdLiveData) { bookId ->
            DatabaseRepository.getFirstChapterWithBookId(bookId)
        }

    fun getFirstChapterWithBookId(bookId: Long) {
        pGetFirstChapterWithBookIdLiveData.value = bookId
    }

    // 获取章节内容
    private val pGetChapterContentLiveData = MutableLiveData<Chapter>()
    private val pPreChapterLiveData = MutableLiveData<Chapter?>()
    private val pNextChapterLiveData = MutableLiveData<Chapter?>()

    var preChapter = StringBuilder()
    var readingChapterContent = StringBuilder()
    var nextChapter = StringBuilder()

    val preChapterLiveData =
        Transformations.switchMap(pPreChapterLiveData) { chapter ->
            chapter?.let {
                ChapterRepository.getChapterContent(chapter)
            }
        }
    val curChapterLiveData =
        Transformations.switchMap(pGetChapterContentLiveData) { chapter ->
            ChapterRepository.getChapterContent(chapter)
        }
    val nextChapterLiveData =
        Transformations.switchMap(pNextChapterLiveData) { chapter ->
            chapter?.let {
                ChapterRepository.getChapterContent(chapter)
            }
        }

    fun getChapterContent() {
        pGetChapterContentLiveData.value = readingChapter
        /*val index = loadChaptersWithBookId.indexOf(readingChapter)
        // 配置上一章
        if (index == 0) {
            pPreChapterLiveData.value = null
        } else {
            pPreChapterLiveData.value = loadChaptersWithBookId[index - 1]
        }
        // 配置下一章
        if (index == loadChaptersWithBookId.size - 1) {
            pNextChapterLiveData.value = null
        } else {
            pNextChapterLiveData.value = loadChaptersWithBookId[index + 1]
        }*/
    }

    fun getPreChapterContent() {
        val index = loadChaptersWithBookId.indexOf(readingChapter)
        if (index == 0) {
            pPreChapterLiveData.value = null
        } else {
            pPreChapterLiveData.value = loadChaptersWithBookId[index - 1]
        }
    }

    fun getNextChapterContent() {
        val index = loadChaptersWithBookId.indexOf(readingChapter)
        if (index == loadChaptersWithBookId.size - 1) {
            pNextChapterLiveData.value = null
        } else {
            pNextChapterLiveData.value = loadChaptersWithBookId[index + 1]
        }
    }

    fun getNextChapterId() = pNextChapterLiveData.value?.chapterId
    fun getPreChapterId() = pPreChapterLiveData.value?.chapterId

    /**
     * 点开章节时的起始位置，默认为 0
     */
    var contentNextStartIndex = 0

    companion object {
        // 小说页默认显示中间页面
        const val DEFAULT_ITEM_ID = 1
    }

    //private val pSelectedChapter =
}