package com.songlan.deepink.ui.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.songlan.deepink.R
import com.songlan.deepink.model.Book

class SearchBookActivityVM : ViewModel() {

    companion object {
        const val DEFAULT_FRAGMENT = 0
        const val SEARCH_HISTORY_FRAGMENT = 0
        const val SEARCH_RESULT_FRAGMENT = 1
    }
    
    // 查询条件，稍后考虑改为LiveData
    var searchBookName: String = ""

    val searchBookHistoryFragment: Fragment by lazy {
        SearchBookHistoryFragment()
    }
    val searchBookResultFragment: SearchBookResultFragment by lazy {
        SearchBookResultFragment()
    }

    val searchBookHistoryList by lazy {
        val it = mutableListOf<String>()
        it.add("斗破苍穹")
        it.add("武动乾坤")
        it.add("斗罗大陆")
        it.add("全职法师")
        it.add("全球高武")
        it
    }

    val searchBookHotBookList by lazy {
        val it = mutableListOf<String>()
        it.add("超神机械师")
        it.add("吞噬星空")
        it.add("大奉打更人")
        it.add("剑来")
        it.add("大王饶命")
        it.add("我师兄实在太稳了")
        it.add("小阁老")
        it.add("精灵掌门人")
        it
    }

    val searchBookResultList by lazy {
        val it = mutableListOf<Book>()
        it.add(Book(R.drawable.ic_book_default, "断舍离"))
        it
    }
}