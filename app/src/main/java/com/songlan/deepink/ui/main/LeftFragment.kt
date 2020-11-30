package com.songlan.deepink.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import com.songlan.deepink.model.Bookshelf
import kotlinx.android.synthetic.main.fragment_main_left.*

class LeftFragment : Fragment() {

    private var bookshelfList = mutableListOf<Bookshelf>()
    private val bookList = mutableListOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 临时数据
        bookList.add(Book(R.drawable.ic_book_default, "易筋经"))
        bookList.add(Book(R.drawable.ic_book_default, "洗髓经"))
        bookList.add(Book(R.drawable.ic_book_default, "金刚经"))
        bookList.add(Book(R.drawable.ic_book_default, "四十二章经"))
        val bookshelf = Bookshelf("正在阅读", bookList)
        bookshelfList.add(bookshelf)

        return inflater.inflate(R.layout.fragment_main_left, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 配置顶部工具栏
        main_left_toolbar.inflateMenu(R.menu.main_left_toolbar_menu)

        // 配置书架展示部分
        val manager = LinearLayoutManager(activity)
        val adapter = MyRecyclerViewAdapter(bookshelfList)
        main_left_bookshelf_recycler.layoutManager = manager
        main_left_bookshelf_recycler.adapter = adapter
    }

    inner class MyRecyclerViewAdapter(private val bookshelfList: List<Bookshelf>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val bookshelfItemChecked: CheckBox = view.findViewById(R.id.itemCheckbox)
            val bookshelfName: TextView = view.findViewById(R.id.itemName)
            val bookshelfDetails: RecyclerView = view.findViewById(R.id.item_main_left_details)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(R.layout.item_main_left, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val bookshelf = bookshelfList[position]
            holder.bookshelfItemChecked.isChecked = true
            holder.bookshelfName.text = bookshelf.bookshelfName
            holder.bookshelfDetails.adapter =
                DetailsAdapter(bookshelf.bookList)
            val layoutManager = LinearLayoutManager(requireContext())
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            holder.bookshelfDetails.layoutManager = layoutManager
        }

        override fun getItemCount() = bookshelfList.size

        inner class DetailsAdapter(val bookList: List<Book>) :
            RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {
            inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                val detailsImage: ImageView = view.findViewById(R.id.detailsImage)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = layoutInflater.inflate(R.layout.item_main_left_details, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val book = bookList[position]
                holder.detailsImage.setImageResource(book.bookImage)
            }

            override fun getItemCount() = bookList.size

        }

    }
}