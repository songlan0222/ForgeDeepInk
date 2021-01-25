package com.songlan.deepink.ui.read

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.model.app.ReadPageMenuItem
import com.songlan.deepink.ui.base.BaseFragment
import com.songlan.deepink.utils.ConfigUtil
import kotlinx.android.synthetic.main.fragment_book_more.*
import java.lang.Exception
import java.lang.RuntimeException

class BookMoreFragment(layout: Int = R.layout.fragment_book_more) : BaseFragment(layout) {

    private lateinit var currentActivity: ReadBookActivity
    private lateinit var viewModel: ReadBookActivityVM
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentActivity = requireActivity() as ReadBookActivity
        viewModel = currentActivity.viewModel

        val manager = GridLayoutManager(currentActivity, 4)
        adapter = MyRecyclerViewAdapter(viewModel.readPageMenuItems)
        settingItems.layoutManager = manager
        settingItems.adapter = adapter

        viewModel.readPageMenuItemsLiveData.observe(currentActivity, Observer { result ->
            val items = result.getOrNull()
            if (items != null) {
                viewModel.readPageMenuItems.clear()
                viewModel.readPageMenuItems.addAll(items)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.getReadPageMenuItems()
    }

    inner class MyRecyclerViewAdapter(private val items: List<ReadPageMenuItem>) :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.imageView)
            val textView: TextView = view.findViewById(R.id.textView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_read_page_settings, parent, false)
            val holder = ViewHolder(view)
            return holder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.let {
                it.imageView.setImageResource(items[position].itemImageId)
                it.textView.text = items[position].itemName
                it.itemView.isSelected = items[position].itemSelected
            }


        }

        override fun getItemCount() = items.size
    }
}