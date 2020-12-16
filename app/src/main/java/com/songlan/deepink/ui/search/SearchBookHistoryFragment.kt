package com.songlan.deepink.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.ui.component.AutoLineFeedLayoutManager
import kotlinx.android.synthetic.main.activity_search_book.*
import kotlinx.android.synthetic.main.fragment_search_book_history.*

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
            searchBookActivity.vm.searchBookHotBookList,
            DataType.HOT_BOOK
        )
        recyclerView_hotBook.layoutManager = AutoLineFeedLayoutManager()

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
            val historyText: TextView = view.findViewById(R.id.textView_history)
            val hotLogo: ImageView = view.findViewById(R.id.image_hotLogo)
            val historyLayout: LinearLayout = view.findViewById(R.id.linearLayout_history)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return if (dataType == DataType.HISTORY) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_book_history, parent, false)
                val viewHolder = HistoryViewHolder(view)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    val history = dataList[position]
                    searchBookActivity.editText_searchBookName.setText(history)

                }
                viewHolder
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_book_hot, parent, false)
                val viewHolder = HotBookViewHolder(view)
                viewHolder.itemView.setOnClickListener {
                    val position = viewHolder.adapterPosition
                    val bookName = dataList[position]
                    searchBookActivity.editText_searchBookName.setText(bookName)
                }
                viewHolder
            }

        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (dataType == DataType.HISTORY) {
                val history = dataList[position]
                holder as HistoryViewHolder
                holder.history.text = history
            } else {
                val bookName = dataList[position]
                holder as HotBookViewHolder
                holder.historyText.text = bookName
                if (position < 3) {
                    holder.historyText.setTextColor(
                        ContextCompat.getColor(
                            searchBookActivity,
                            R.color.crimson
                        )
                    )
                    holder.hotLogo.visibility = View.VISIBLE
                } else {

                    holder.historyText.setTextColor(R.color.black)
                    holder.historyLayout.background =
                        ContextCompat.getDrawable(
                            searchBookActivity,
                            R.drawable.selector_layout_click_normal
                        )
                    holder.historyLayout.setPadding(40, 0, 40, 0)
                }
            }

        }

        override fun getItemCount(): Int = if (dataList.size < 20) dataList.size else 20
    }

}