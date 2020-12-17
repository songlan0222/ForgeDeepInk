package com.songlan.deepink.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.songlan.deepink.R
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

    var checkedBookshelf = Bookshelf("默认", true)
    val checkedBookList = ArrayList<Book>()
    val bookshelfList = ArrayList<Bookshelf>()

    val bookshelfLiveData = Transformations.switchMap(checkedBookshelfLiveData) { bookshelfId ->
        DatabaseRepository.getBookshelfWithId(bookshelfId)
    }
    val bookListLiveData = Transformations.switchMap(checkedBookshelfLiveData) { bookshelfId ->
        DatabaseRepository.getBookList(bookshelfId)
    }
    val bookshelfListLiveData = Transformations.switchMap(allBookshelfListLiveData) {
        DatabaseRepository.getBookshelfList()
    }

    fun checkedBookshelf(query: Long) {
        checkedBookshelfLiveData.value = query
    }

    fun getBookshelfList() {
        allBookshelfListLiveData.value = allBookshelfListLiveData.value
    }

//    init {
//        bookshelfList.clear()
//        val bookList = mutableListOf<Book>()
//        bookList.clear()
//        bookList.add(Book(R.drawable.ic_book_default, "易筋经"))
//        bookList.add(Book(R.drawable.ic_book_default, "洗髓经"))
//        bookList.add(Book(R.drawable.ic_book_default, "金刚经"))
//        bookList.add(Book(R.drawable.ic_book_default, "四十二章经"))
//        bookshelfList.add(Bookshelf(0, "正在阅读", bookList))
//    }
}