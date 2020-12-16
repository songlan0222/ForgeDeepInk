package com.songlan.deepink.ui.main

import androidx.lifecycle.ViewModel
import com.songlan.deepink.model.BookSrc

class BookSrcManageActivityVM : ViewModel() {

    val bookSrcList by lazy {
        val it = mutableListOf<BookSrc>()
        it.add(BookSrc())
        it.add(BookSrc())
        it.add(BookSrc())
        it
    }
}