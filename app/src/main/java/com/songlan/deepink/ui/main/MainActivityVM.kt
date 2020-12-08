package com.songlan.deepink.ui.main

import androidx.lifecycle.ViewModel
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf

class MainActivityVM : ViewModel() {
    @Suppress("PropertyName")
    val DEFAULT_ITEM_ID = 1
    // 暂定为 0，后续变为用户可更改数据
    var CURRENT_BOOKSHELF_ID = 0

    val bookshelfList = mutableListOf<Bookshelf>()

    init {
        bookshelfList.clear()
        val bookList = mutableListOf<Book>()
        bookList.clear()
        bookList.add(Book(R.drawable.ic_book_default, "易筋经"))
        bookList.add(Book(R.drawable.ic_book_default, "洗髓经"))
        bookList.add(Book(R.drawable.ic_book_default, "金刚经"))
        bookList.add(Book(R.drawable.ic_book_default, "四十二章经"))
        bookshelfList.add(Bookshelf(0, "正在阅读", bookList))
    }
}