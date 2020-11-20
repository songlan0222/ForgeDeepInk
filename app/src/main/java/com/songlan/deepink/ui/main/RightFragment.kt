package com.songlan.deepink.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hjq.bar.OnTitleBarListener
import com.songlan.deepink.MyApplication
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_main_left.*
import kotlinx.android.synthetic.main.fragment_main_right.*

class RightFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_right, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 配置顶部TitleBar
        main_right_titleBar.leftIcon =
            ContextCompat.getDrawable(MyApplication.context, R.drawable.ic_main_right_experiencer)
        main_right_titleBar.rightIcon =
            ContextCompat.getDrawable(MyApplication.context, R.drawable.ic_main_right_subscriber)

        // 添加点击事件
        main_right_titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
            }

            // 此处用不上中间的点击事件
            override fun onTitleClick(v: View?) {
            }

            override fun onRightClick(v: View?) {
            }

        })
    }
}