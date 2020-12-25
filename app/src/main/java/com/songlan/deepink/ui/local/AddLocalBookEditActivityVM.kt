package com.songlan.deepink.ui.local

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.songlan.deepink.model.Book
import com.songlan.deepink.repository.ChapterRepository
import com.songlan.deepink.repository.DatabaseRepository

class AddLocalBookEditActivityVM : ViewModel() {

    private val pChapterTitlesLiveData = MutableLiveData<Uri>()
    private val pInsertBookLiveData = MutableLiveData<Book>()

    val chapterTitles = mutableListOf<String>()

    val chapterTitlesLiveData = Transformations.switchMap(pChapterTitlesLiveData) { uri ->
        ChapterRepository.getChapterTitlesFromTxt(uri)
    }
    val insertBookLiveData = Transformations.switchMap(pInsertBookLiveData) { book ->
        DatabaseRepository.insertBook(book)
    }

    fun getChapterTitlesFromTxt(uri: Uri) {
        pChapterTitlesLiveData.value = uri
    }

    fun insertBook(book: Book) {
        pInsertBookLiveData.value = book
    }

}