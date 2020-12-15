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
}