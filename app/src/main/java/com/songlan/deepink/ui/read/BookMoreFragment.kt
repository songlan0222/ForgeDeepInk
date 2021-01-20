package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_book_more.*

class BookMoreFragment : Fragment() {
    private lateinit var readBookActivity: ReadBookActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
        }
        LogUtils.v(msg = "加载BookMoreFragment")
        return inflater.inflate(R.layout.fragment_book_more, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingItems.layoutManager = GridLayoutManager(readBookActivity, 4)
    }

    inner class MyRecyclerViewAdapter() :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_read_page_settings, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val settingItem = itemList[position]

        }

        override fun getItemCount() = 0
    }
}