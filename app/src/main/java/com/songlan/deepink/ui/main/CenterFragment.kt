package com.songlan.deepink.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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

        main_toolbar.inflateMenu(R.menu.main_toolbar_menu)
        main_toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.main_bookshelf_search -> {
                }
                R.id.main_bookshelf_me -> {
                }
            }
            true
        }

        // 添加工具栏正中标题
        main_toolbar?.title = getString(R.string.bookshelf_name)

        // 下拉刷新功能
        main_bookshelf_swipe_refresh.setOnRefreshListener {
            main_bookshelf_swipe_refresh.isRefreshing = false
        }
    }
}