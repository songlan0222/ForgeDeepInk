package com.songlan.deepink.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_search_book_history.*

class SearchBookHistoryFragment : Fragment() {

    private lateinit var searchBookActivity: SearchBookActivity

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

        recyclerView_searchHistory.adapter =
            MyRecyclerViewAdapter(searchBookActivity.vm.searchBookHistoryList)
        recyclerView_searchHistory.layoutManager = LinearLayoutManager(searchBookActivity)

    }

    inner class MyRecyclerViewAdapter(private val historyList: List<String>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val history: TextView = view.findViewById(R.id.textView_history)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_book_history, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                val position = viewHolder.adapterPosition
                val history = historyList[position]
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val history = historyList[position]
            holder.history.text = history
        }

        override fun getItemCount(): Int = if (historyList.size < 20) historyList.size else 20
    }

}