package com.songlan.deepink.ui.main

import android.content.Intent
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
import com.songlan.deepink.ui.settings.SettingActivity
import kotlinx.android.synthetic.main.fragment_bookshelf_others.*


class BookshelfOthersFragment : BaseFragment(), View.OnClickListener {

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

        // 配置选项点击的监听器
        localBook.setOnClickListener(this)
        bookSrc.setOnClickListener(this)
        bookRank.setOnClickListener(this)

        rss.setOnClickListener(this)
        community.setOnClickListener(this)
        settings.setOnClickListener(this)

    }

    // 重写点击返回按钮时需要调用的方法
    override fun onBackPressed(): Boolean {
        mainActivity.changeFragment(MainActivityVM.BOOKSHELF_DETAILS_FRAGMENT_ID)
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.settings -> {
                val intent = Intent(this.context, SettingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}