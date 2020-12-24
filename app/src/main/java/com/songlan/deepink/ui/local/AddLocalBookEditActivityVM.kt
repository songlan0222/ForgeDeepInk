package com.songlan.deepink.ui.local

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.repository.ChapterRepository

class AddLocalBookEditActivityVM : ViewModel() {

    private val pChapterTitlesLiveData = MutableLiveData<Uri>()

    val chapterTitles = mutableListOf<String>()

    val chapterTitlesLiveData = Transformations.switchMap(pChapterTitlesLiveData) { uri ->
        ChapterRepository.getChapterTitlesFromTxt(uri)
    }

    fun getChapterTitlesFromTxt(uri: Uri) {
        pChapterTitlesLiveData.value = uri
    }

}