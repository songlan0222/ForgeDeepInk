package com.songlan.deepink.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.Book
import kotlinx.android.synthetic.main.fragment_search_book_result.*

class SearchBookResultFragment : Fragment() {

    private lateinit var searchBookActivity: SearchBookActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            searchBookActivity = activity as SearchBookActivity
        }
        return inflater.inflate(R.layout.fragment_search_book_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView_searchResult.layoutManager = LinearLayoutManager(this.activity)
        recyclerView_searchResult.adapter =
            MyRecyclerViewAdapater(searchBookActivity.vm.searchBookResultList)
    }

    inner class MyRecyclerViewAdapater(private val resultList: List<Book>) :
        RecyclerView.Adapter<MyRecyclerViewAdapater.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyRecyclerViewAdapater.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_book_result, parent, false)
            val viewHolder = ViewHolder(view)
            return viewHolder
        }

        override fun onBindViewHolder(
            holder: MyRecyclerViewAdapater.ViewHolder,
            position: Int
        ) {
            val position = holder.adapterPosition
            val book = resultList[position]
        }

        override fun getItemCount() = resultList.size

    }

}