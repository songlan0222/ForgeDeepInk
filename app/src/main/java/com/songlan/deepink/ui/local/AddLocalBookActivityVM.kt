package com.songlan.deepink.ui.local

import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.repository.DatabaseRepository

class AddLocalBookActivityVM : ViewModel() {

    private val pLoadPersistedFilesLiveData = MutableLiveData<Any?>()

    val persistedFiles = mutableListOf<DocumentFile?>()

    val loadPersistedFilesLiveData = Transformations.switchMap(pLoadPersistedFilesLiveData) {
        DatabaseRepository.loadPersistedFiles()
    }

    fun loadPersistedFiles() {
        pLoadPersistedFilesLiveData.value = pLoadPersistedFilesLiveData.value
    }


}