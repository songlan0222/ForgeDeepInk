package com.songlan.deepink.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import kotlinx.android.synthetic.main.fragment_main_center.*

class CenterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MainTest", "加载中央Fragment")
        return inflater.inflate(R.layout.fragment_main_center, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        main_center_toolbar.inflateMenu(R.menu.main_toolbar_menu)
        main_center_toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.main_bookshelf_search -> {
                }
                R.id.main_bookshelf_me -> {
                }
            }
            true
        }

        // 添加工具栏正中标题
        main_center_toolbar?.title = getString(R.string.bookshelf_name)

        // 下拉刷新功能
        main_center_bookshelf_swipe_refresh.setOnRefreshListener {
            main_center_bookshelf_swipe_refresh.isRefreshing = false
        }
    }

    private inner class MyXRecyclerViewAdapter(val context: Context, val bookList: List<Book>) :
        RecyclerView.Adapter<MyXRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val bookImage: ImageView = view.findViewById(R.id.bookImage)
            val bookName: TextView = view.findViewById(R.id.bookName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bookshelf_books, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {

            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val book = bookList[position]
            holder.bookImage.setImageResource(book.bookImage)
            holder.bookName.text = book.bookName
        }

        override fun getItemCount() = bookList.size
    }
}