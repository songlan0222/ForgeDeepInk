package com.songlan.deepink.ui.read

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_book_directory.*

class BookDirectoryFragment : Fragment() {

    private lateinit var readBookActivity: ReadBookActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
        }
        LogUtils.v(msg = "加载BookDirectoryFragment")
        return inflater.inflate(R.layout.fragment_book_directory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 配置工具栏内容
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

            }
            true
        }
        toolbar?.title = "斗破苍穹"

        toolbar?.setNavigationIcon(R.drawable.ic_back)
        toolbar?.setNavigationOnClickListener {
            readBookActivity.finish()
        }

        chapterTitleList.layoutManager = LinearLayoutManager(readBookActivity)
        chapterTitleList.adapter = readBookActivity.chapterTitleAdapter

    }


}