package com.songlan.deepink.ui.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songlan.deepink.ui.component.ReadPage
import com.songlan.deepink.ui.read.ReadBookActivity

abstract class BasePageFragment(layout: Int) : BaseFragment(layout) {
    lateinit var readBookActivity: ReadBookActivity
    lateinit var readPageConfig: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
            readPageConfig = readBookActivity.viewModel.readPageConfig
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun initReadPage(readPage: ReadPage) {
        readPage.text = ""
        readPage?.let {
            it.textSize = readPageConfig.getFloat("textSize", 14F)
            it.textScaleX = readPageConfig.getFloat("textScaleX", 1F)
            it.setLineSpacing(readPageConfig.getFloat("lineSpacing", 1F), 1F)
        }
    }


}