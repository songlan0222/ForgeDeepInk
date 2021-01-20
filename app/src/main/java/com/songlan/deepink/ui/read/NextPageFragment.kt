package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.View
import com.songlan.deepink.R
import com.songlan.deepink.ui.base.BasePageFragment
import kotlinx.android.synthetic.main.fragment_last_page.*

class NextPageFragment(layout: Int = R.layout.fragment_last_page) : BasePageFragment(layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadPage(nextReadPage)
    }
}