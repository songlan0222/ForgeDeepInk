package com.songlan.deepink.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_main_left.*

class LeftFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_left, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 配置顶部工具栏
        main_left_toolbar.inflateMenu(R.menu.main_left_toolbar_menu)
    }
}