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
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_current_page.*
import kotlinx.android.synthetic.main.fragment_last_page.*
import kotlinx.android.synthetic.main.fragment_pre_page.*


class CurPageFragment : Fragment() {

    private lateinit var readBookActivity: ReadBookActivity
    private lateinit var readPageConfig: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
            readPageConfig = readBookActivity.viewModel.readPageConfig
        }

        return inflater.inflate(R.layout.fragment_current_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        curReadPage.setOnClickListener {
            readBookActivity.showBottomSheetDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadPage()
    }

    private fun initReadPage() {
        curReadPage.text = ""
        curReadPage?.let {
            it.textSize = readPageConfig.getFloat("textSize", 14F)
            it.textScaleX = readPageConfig.getFloat("textScaleX", 1F)
            it.setLineSpacing(readPageConfig.getFloat("lineSpacing", 1F), 1F)
        }

    }
}