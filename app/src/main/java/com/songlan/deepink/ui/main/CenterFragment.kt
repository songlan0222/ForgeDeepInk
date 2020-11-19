package com.songlan.deepink.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_main_view.*

class CenterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MainTest", "加载中央Fragment")
        return inflater.inflate(R.layout.fragment_main_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // setSupportActionBar(main_toolbar)
        // 配置工具栏左边的书架管理按钮
        // supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_bookshelf)
        // 添加工具栏正中标题
        main_toolbar?.title = getString(R.string.bookshelf_name)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_bookshelf_search -> {
            }
            R.id.main_bookshelf_me -> {
            }
        }
        return true
    }
}