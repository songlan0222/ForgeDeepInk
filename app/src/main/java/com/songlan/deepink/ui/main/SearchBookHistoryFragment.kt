package com.songlan.deepink.ui.main

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_search_book_history.*
import kotlinx.android.synthetic.main.item_search_book_hot.*

class SearchBookHistoryFragment : Fragment() {

    private lateinit var searchBookActivity: SearchBookActivity

    enum class DataType {
        HOT_BOOK, HISTORY
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            searchBookActivity = activity as SearchBookActivity
        }
        return inflater.inflate(R.layout.fragment_search_book_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView_hotBook.adapter = MyRecyclerViewAdapter(
            searchBookActivity.vm.SearchBookActivityHotBookList,
            DataType.HOT_BOOK
        )
        recyclerView_hotBook.layoutManager = LinearLayoutManager(searchBookActivity)

        recyclerView_searchHistory.adapter =
            MyRecyclerViewAdapter(searchBookActivity.vm.searchBookHistoryList, DataType.HISTORY)
        recyclerView_searchHistory.layoutManager = LinearLayoutManager(searchBookActivity)

    }

    inner class MyRecyclerViewAdapter(
        private val dataList: List<String>,
        private val dataType: DataType
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val history: TextView = view.findViewById(R.id.textView_history)
        }

        inner class HotBookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val hotLogo: ImageView = view.findViewById(R.id.imageView_hotLogo)
            val bookName: TextView = view.findViewById(R.id.textView_hotBookName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return if (dataType == DataType.HISTORY) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_book_history, parent, false)
                val viewHolder = HistoryViewHolder(view)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    val history = dataList[position]
                }
                viewHolder
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_book_hot, parent, false)
                val viewHolder = HotBookViewHolder(view)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    val bookName = dataList[position]
                }
                viewHolder
            }

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (dataType == DataType.HISTORY) {
                val history = dataList[position]
                holder as HistoryViewHolder
                holder.history.text = history
            } else {
                val bookName = dataList[position]
                holder as HotBookViewHolder
                holder.bookName.text = bookName
                if (position < 3) {
                    holder.hotLogo.setImageResource(R.drawable.ic_search_book_hot)
                    holder.hotLogo.visibility = View.VISIBLE
                } else {
                    holder.hotLogo.visibility = View.GONE
                }
            }

        }

        override fun getItemCount(): Int = if (dataList.size < 20) dataList.size else 20
    }

}