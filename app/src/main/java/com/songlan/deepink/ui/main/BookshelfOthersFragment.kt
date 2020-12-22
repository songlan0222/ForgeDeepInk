package com.songlan.deepink.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.ui.main.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_bookshelf_others.*


class BookshelfOthersFragment : BaseFragment() {

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (activity != null) {
            mainActivity = activity as MainActivity
        }

        val view = inflater.inflate(R.layout.fragment_bookshelf_others, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    // 重写点击返回按钮时需要调用的方法
    override fun onBackPressed(): Boolean {
        mainActivity.changeFragment(MainActivityVM.BOOKSHELF_DETAILS_FRAGMENT_ID)
        return true
    }
}