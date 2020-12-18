package com.songlan.deepink.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.repository.DatabaseRepository

class EditBookshelfActivityVM : ViewModel() {
    private val pBookshelfIdLiveData = MutableLiveData<Long>()

    val checkedBookshelf = Bookshelf("")

    val checkedBookshelfLiveData = Transformations.switchMap(pBookshelfIdLiveData) { bookshelfId ->
        DatabaseRepository.loadBookshelf(bookshelfId)
    }

    fun loadBookshelf(query: Long) {
        pBookshelfIdLiveData.value = query
    }

    fun insertBookshelf(bookshelf: Bookshelf) {

    }
}