package com.songlan.deepink.ui.read

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.Chapter
import com.songlan.deepink.utils.LogUtils

class BookDirectoryFragment : Fragment() {

    private lateinit var readBookActivity: ReadBookActivity
    lateinit var chapterTitleAdapter: MyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
        }
        LogUtils.v(msg="加载BookDirectoryFragment")
        return inflater.inflate(R.layout.fragment_book_directory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        readBookActivity.viewModel.loadChaptersWithBookId.observe(readBookActivity, Observer { result ->
//            val chapters = result.getOrNull()
//            if (chapters != null) {
//                readBookActivity.viewModel.chapterTitles.clear()
//                readBookActivity.viewModel.chapterTitles.addAll(chapters)
//                chapterTitleAdapter.notifyDataSetChanged()
//            }
//        })
//        chapterTitleAdapter = MyRecyclerViewAdapter(readBookActivity.viewModel.chapterTitles)
//
//        // 配置工具栏内容
//        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
//        toolbar?.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//
//            }
//            true
//        }
//        toolbar?.title = "斗破苍穹"
//
//        toolbar?.setNavigationIcon(R.drawable.ic_back)
//        toolbar?.setNavigationOnClickListener {
//            readBookActivity.finish()
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    inner class MyRecyclerViewAdapter(private val chapterList: List<Chapter>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chapter_title, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                val position = viewHolder.adapterPosition
                val chapter = chapterList[position]
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val chapter = chapterList[position]

        }

        override fun getItemCount() = chapterList.size
    }
}