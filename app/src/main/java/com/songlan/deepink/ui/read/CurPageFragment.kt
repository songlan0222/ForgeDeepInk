package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.View
import com.songlan.deepink.R
import com.songlan.deepink.ui.base.BasePageFragment
import kotlinx.android.synthetic.main.fragment_current_page.*


class CurPageFragment(layout: Int = R.layout.fragment_current_page) : BasePageFragment(layout) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        curReadPage.setOnClickListener {
            readBookActivity.showBottomSheetDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadPage(curReadPage)
    }


}