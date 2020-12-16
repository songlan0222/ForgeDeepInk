package com.songlan.deepink.ui.main

import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.BookSrc

class BookSrcManageActivityVM : ViewModel() {

    val bookSrcList by lazy {
        val it = mutableListOf<BookSrc>()
        it.add(BookSrc("九桃小说"))
        it.add(BookSrc("起点中文"))
        it.add(BookSrc("纵横中文"))
        it
    }
}