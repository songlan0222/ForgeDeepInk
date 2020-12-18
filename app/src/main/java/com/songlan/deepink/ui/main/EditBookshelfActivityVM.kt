package com.songlan.deepink.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.repository.DatabaseRepository

class EditBookshelfActivityVM : ViewModel() {
    private val pBookshelfIdLiveData = MutableLiveData<Long>()
    private val pBookshelfLiveData = MutableLiveData<Bookshelf>()

    val checkedBookshelf = Bookshelf("")

    val checkedBookshelfLiveData = Transformations.switchMap(pBookshelfIdLiveData) { bookshelfId ->
        DatabaseRepository.loadBookshelf(bookshelfId)
    }
    val insertBookshelfLiveData = Transformations.switchMap(pBookshelfLiveData) { bookshelf ->
        DatabaseRepository.insertBookshelf(bookshelf)
    }
    val updateBbookshelfLiveData = Transformations.switchMap(pBookshelfLiveData) { bookshelf ->
        DatabaseRepository.updateBookshelf(bookshelf)
    }

    fun loadBookshelf(query: Long) {
        pBookshelfIdLiveData.value = query
    }

    fun insertBookshelf(bookshelf: Bookshelf) {

    }

    fun updateBookshelf(bookshelf: Bookshelf) {

    }
}