package com.songlan.deepink.ui.read

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.repository.DatabaseRepository

class ReadBookActivityVM : ViewModel() {
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
}