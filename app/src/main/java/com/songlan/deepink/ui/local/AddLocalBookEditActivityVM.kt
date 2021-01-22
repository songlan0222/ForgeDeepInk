package com.songlan.deepink.ui.local

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.data.Book
import com.songlan.deepink.model.data.Chapter
import com.songlan.deepink.repository.ChapterRepository
import com.songlan.deepink.repository.DatabaseRepository

class AddLocalBookEditActivityVM : ViewModel() {

    // 获取数据库章节目录
    private val pChapterTitlesLiveData = MutableLiveData<Uri>()
    val chapterTitles = mutableListOf<String>()
    val chapterTitlesLiveData = Transformations.switchMap(pChapterTitlesLiveData) { uri ->
        ChapterRepository.getChapterTitlesFromTxt(uri)
    }

    fun getChapterTitlesFromTxt(uri: Uri) {
        pChapterTitlesLiveData.value = uri
    }

    // 向数据库添加书籍
    private val pInsertBookLiveData = MutableLiveData<Book>()
    val insertBookLiveData = Transformations.switchMap(pInsertBookLiveData) { book ->
        DatabaseRepository.insertBook(book)
    }

    fun insertBook(book: Book) {
        pInsertBookLiveData.value = book
    }

    // 向数据库添加章节
    private val pChapterLiveData = MutableLiveData<Chapter>()
    val chapterLiveData = Transformations.switchMap(pChapterLiveData) { chapter ->
        DatabaseRepository.insertChapter(chapter)

    }
    fun insertChapter(chapter: Chapter) {
        pChapterLiveData.value = chapter
    }
}