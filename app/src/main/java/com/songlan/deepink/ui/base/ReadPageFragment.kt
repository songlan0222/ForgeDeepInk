package com.songlan.deepink.ui.base

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.songlan.deepink.ui.component.ReadPage
import com.songlan.deepink.ui.read.ReadBookActivity

abstract class ReadPageFragment(layout: Int) : BaseFragment(layout) {

    lateinit var readPageConfig: SharedPreferences
    lateinit var currentActivity: ReadBookActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (activity != null) {
            currentActivity = activity as ReadBookActivity
            readPageConfig = currentActivity.viewModel.readPageConfig
        }
        return view
    }

    fun initReadPage(readPage: ReadPage) {
        readPage.text = ""
        readPage.let {
            it.textSize = readPageConfig.getFloat("textSize", 14F)
            it.textScaleX = readPageConfig.getFloat("textScaleX", 1F)
            it.setLineSpacing(readPageConfig.getFloat("lineSpacing", 1F), 1F)
        }
    }


}