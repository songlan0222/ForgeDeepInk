package com.songlan.deepink.ui.read

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.test.internal.util.LogUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.songlan.deepink.R
import com.songlan.deepink.ui.read.base.BasePageFragment
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_current_page.*
import kotlinx.android.synthetic.main.fragment_last_page.*
import kotlinx.android.synthetic.main.fragment_pre_page.*


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