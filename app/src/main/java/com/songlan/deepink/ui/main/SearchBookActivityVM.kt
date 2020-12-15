package com.songlan.deepink.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

class SearchBookActivityVM : ViewModel() {

    companion object {
        const val DEFAULT_FRAGMENT = 0
        const val SEARCH_HISTORY_FRAGMENT = 0
        const val SEARCH_RESULT_FRAGMENT = 1
    }

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
}