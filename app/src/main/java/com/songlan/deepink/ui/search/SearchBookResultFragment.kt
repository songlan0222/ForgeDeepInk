package com.songlan.deepink.ui.search

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

    enum class ResultType {
        FROM_INTERNET, FROM_LOCAL
    }

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
        recyclerView_searchResult.layoutManager = LinearLayoutManager(searchBookActivity)
        recyclerView_searchResult.adapter =
            MyRecyclerViewAdapter(searchBookActivity.vm.searchBookResultList, ResultType.FROM_LOCAL)

        // 配置本地图书
        recyclerView_localBooks.layoutManager =
            LinearLayoutManager(searchBookActivity, LinearLayoutManager.HORIZONTAL, false)
        val searchResultFromLocal = searchFromBookshelf(searchBookActivity.vm.searchBookName)
        recyclerView_localBooks.adapter =
            MyRecyclerViewAdapter(searchResultFromLocal, ResultType.FROM_LOCAL)
    }

    // 根据字段，在数据库中查询本地图书
    private fun searchFromBookshelf(searchBookName: String): List<Book> {

        return listOf<Book>()
    }

    inner class MyRecyclerViewAdapter(
        private val resultList: List<Book>,
        private val resultType: ResultType
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        inner class ViewHolderFromInternet(view: View) : RecyclerView.ViewHolder(view) {

        }

        inner class ViewHolderFromLocal(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {

            return if (resultType == ResultType.FROM_INTERNET) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_book_result_from_internet, parent, false)
                val viewHolder = ViewHolderFromInternet(view)
                viewHolder
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_book_result_from_local, parent, false)
                val viewHolder = ViewHolderFromInternet(view)
                viewHolder
            }

        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int
        ) {
            if (resultType == ResultType.FROM_INTERNET) {
                val position = holder.adapterPosition
                val book = resultList[position]
            } else {
                val position = holder.adapterPosition
                val book = resultList[position]
            }
        }

        override fun getItemCount() = resultList.size

    }

}