package com.songlan.deepink.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.data.Bookshelf
import com.songlan.deepink.repository.DatabaseRepository

class EditBookshelfActivityVM : ViewModel() {
    private val pBookshelfIdLiveData = MutableLiveData<Long>()
    private val pInsertBookshelfLiveData = MutableLiveData<Bookshelf>()
    private val pUpdateBookshelfLiveData = MutableLiveData<Bookshelf>()

    var checkedBookshelf = Bookshelf("")

    val checkedBookshelfLiveData = Transformations.switchMap(pBookshelfIdLiveData) { bookshelfId ->
        DatabaseRepository.loadBookshelf(bookshelfId)
    }
    val insertBookshelfLiveData = Transformations.switchMap(pInsertBookshelfLiveData) { bookshelf ->
        DatabaseRepository.insertBookshelf(bookshelf)
    }
    val updateBookshelfLiveData = Transformations.switchMap(pUpdateBookshelfLiveData) { bookshelf ->
        DatabaseRepository.updateBookshelf(bookshelf)
    }

    fun loadBookshelf(query: Long) {
        pBookshelfIdLiveData.value = query
    }

    fun insertBookshelf(bookshelf: Bookshelf) {
        pInsertBookshelfLiveData.value = bookshelf
    }

    fun updateBookshelf(bookshelf: Bookshelf) {
        pUpdateBookshelfLiveData.value = bookshelf
    }
}