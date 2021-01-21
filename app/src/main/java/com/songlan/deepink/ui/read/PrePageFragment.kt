package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.View
import com.songlan.deepink.R
import com.songlan.deepink.ui.base.ReadPageFragment
import kotlinx.android.synthetic.main.fragment_pre_page.*

class PrePageFragment(layout: Int = R.layout.fragment_pre_page) : ReadPageFragment(layout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadPage(preReadPage)
    }
}