package com.songlan.deepink.ui.read

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.songlan.deepink.R
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_book_directory.*

class BookDirectoryFragment : Fragment() {

    private lateinit var readBookActivity: ReadBookActivity
    private lateinit var manager: LinearLayoutManager
    private lateinit var adapter: ReadBookActivity.MyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        toolbar?.title = readBookActivity.viewModel.book.bookName

        toolbar?.setNavigationIcon(R.drawable.ic_back)
        toolbar?.setNavigationOnClickListener {
            readBookActivity.finish()
        }

        manager = LinearLayoutManager(readBookActivity)
        adapter = readBookActivity.chapterTitleAdapter
        chapterTitleList.layoutManager = manager
        chapterTitleList.adapter = adapter

        var position = 0
        adapter.chapterList.forEachIndexed{index, chapter->
            if(chapter.chapterId == readBookActivity.viewModel.book.readingChapterId){
                position = index
            }
        }
        moveToPosition(manager, position)
    }

    private fun moveToPosition(manager: LinearLayoutManager, position: Int) {
        val firstItemPosition = manager.findFirstVisibleItemPosition()
        val lastItemPosition = manager.findLastVisibleItemPosition()
        when {
            position <= firstItemPosition -> {
                chapterTitleList.scrollToPosition(position)
            }
            position <= lastItemPosition -> {
                val top = chapterTitleList.getChildAt(position - firstItemPosition).top
                chapterTitleList.scrollBy(0, top)
            }
            else -> {
                chapterTitleList.scrollToPosition(position)
            }
        }
    }
}