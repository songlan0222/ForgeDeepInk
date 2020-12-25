package com.songlan.deepink.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import com.songlan.deepink.repository.DatabaseRepository

class MainActivityVM : ViewModel() {
    companion object {
        const val BOOKSHELF_GROUP_FRAGMENT_ID = 0
        const val BOOKSHELF_DETAILS_FRAGMENT_ID = 1
        const val BOOKSHELF_OTHERS_FRAGMENT_ID = 2
        val DEFAULT_ITEM_ID = BOOKSHELF_DETAILS_FRAGMENT_ID

        // 暂定为 0，后续变为用户可更改数据
        var CURRENT_BOOKSHELF_ID = 0
    }


    // 数据库获取信息
    private val checkedBookshelfLiveData = MutableLiveData<Long>()
    private val allBookshelfListLiveData = MutableLiveData<Any?>()
    private val pDeleteBookshelfLiveData = MutableLiveData<Long>()
    private val pCheckedBookshelfIdLiveData = MutableLiveData<Any?>()
    private val pInsertBookshelfLiveData = MutableLiveData<Bookshelf>()

    var checkedBookshelf = Bookshelf("默认", true)
    val checkedBookList = ArrayList<Book>()
    val bookshelfList = ArrayList<Bookshelf>()

    val bookshelfLiveData = Transformations.switchMap(checkedBookshelfLiveData) { bookshelfId ->
        DatabaseRepository.loadBookshelf(bookshelfId)
    }
    val bookListLiveData = Transformations.switchMap(checkedBookshelfLiveData) { bookshelfId ->
        DatabaseRepository.loadBooksWithBookshelfId(bookshelfId)
    }
    val bookshelfListLiveData = Transformations.switchMap(allBookshelfListLiveData) {
        DatabaseRepository.loadAllBookshelfs()
    }
    val deleteBookshelfLiveData =
        Transformations.switchMap(pDeleteBookshelfLiveData) { bookshelfId ->
            DatabaseRepository.deleteBookshelf(bookshelfId)
        }

    val checkedBookshelfIdLiveData = Transformations.switchMap(pCheckedBookshelfIdLiveData) {
        DatabaseRepository.getCheckedBookshelf()
    }

    val insertBookshelfLiveData = Transformations.switchMap(pInsertBookshelfLiveData) { bookshelf ->
        DatabaseRepository.insertBookshelf(bookshelf)
    }

    fun loadCheckedBookshelf(query: Long) {
        checkedBookshelfLiveData.value = query
    }

    fun loadBookshelfList() {
        allBookshelfListLiveData.value = allBookshelfListLiveData.value
    }

    fun deleteBookshelf(query: Long) {
        pDeleteBookshelfLiveData.value = query
    }

    fun getFirstChooseBookshelfId() {
        pCheckedBookshelfIdLiveData.value = pCheckedBookshelfIdLiveData.value
    }

    fun insertBookshelf(bookshelf: Bookshelf) {
        pInsertBookshelfLiveData.value = bookshelf
    }

}