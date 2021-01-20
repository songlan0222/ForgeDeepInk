package com.songlan.deepink.ui.read

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_last_page.*
import kotlinx.android.synthetic.main.fragment_pre_page.*

class PrePageFragment : Fragment() {

    private lateinit var readBookActivity: ReadBookActivity
    private lateinit var readPageConfig: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
            readPageConfig = readBookActivity.viewModel.readPageConfig
        }
        return inflater.inflate(R.layout.fragment_pre_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadPage()
    }

    private fun initReadPage(){
        preReadPage.text = ""
        preReadPage?.let {
            it.textSize = readPageConfig.getFloat("textSize", 14F)
            it.textScaleX = readPageConfig.getFloat("textScaleX", 1F)
            it.setLineSpacing(readPageConfig.getFloat("lineSpacing", 1F), 1F)
        }
    }

}