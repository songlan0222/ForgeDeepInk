package com.songlan.deepink.ui.read.page

import android.os.Bundle
import android.view.View
import com.songlan.deepink.R
import com.songlan.deepink.ui.base.ReadPageFragment
import kotlinx.android.synthetic.main.fragment_last_page.*

class NextPageFragment(layout: Int = R.layout.fragment_last_page) : ReadPageFragment(layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadPage(nextReadPage)
    }
}