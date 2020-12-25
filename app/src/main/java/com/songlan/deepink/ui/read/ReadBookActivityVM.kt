package com.songlan.deepink.ui.read

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.songlan.deepink.model.Book
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
    val chapterTitles = ArrayList<String>()
    val loadChapterTitleWithBookId =
        Transformations.switchMap(pLoadChapterTitleWithBookId) { bookId ->
            DatabaseRepository.loadChapterTitleWithBookId(bookId)
        }

    fun loadChapterTitleWithBookId(bookId: Long) {
        pLoadChapterTitleWithBookId.value = bookId
    }
}