package com.songlan.deepink.ui.local

import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.repository.DatabaseRepository

class AddLocalBookActivityVM : ViewModel() {

    private val pLoadPersistedFilesLiveData = MutableLiveData<Any?>()
    private val pLoadFilterContentLiveData = MutableLiveData<String>()

    val persistedFiles = mutableListOf<DocumentFile?>()
    val filterFile = mutableListOf<DocumentFile?>()

    val loadPersistedFilesLiveData = Transformations.switchMap(pLoadPersistedFilesLiveData) {
        DatabaseRepository.loadPersistedFiles()
    }

    val loadFilesWithFilterLiveData = Transformations.switchMap(pLoadFilterContentLiveData) { content ->
        DatabaseRepository.loadPersistedFiles(content)
    }

    fun loadPersistedFiles() {
        pLoadPersistedFilesLiveData.value = pLoadPersistedFilesLiveData.value
    }

    fun loadPersistedFilesWithFilter(content: String) {
        pLoadFilterContentLiveData.value = content
    }

}