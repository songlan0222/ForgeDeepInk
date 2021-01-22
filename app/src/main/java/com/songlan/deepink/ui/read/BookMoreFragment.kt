package com.songlan.deepink.ui.read

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.songlan.deepink.R
import com.songlan.deepink.ui.base.BaseFragment
import com.songlan.deepink.utils.ConfigUtil
import kotlinx.android.synthetic.main.fragment_book_more.*

class BookMoreFragment(layout: Int = R.layout.fragment_book_more) : BaseFragment(layout) {

    private lateinit var currentActivity: ReadBookActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentActivity = requireActivity() as ReadBookActivity
        settingItems.layoutManager = GridLayoutManager(currentActivity, 4)
        val items = loadPageMenuItems()
    }

    private fun loadPageMenuItems(){
        val configFileName = "READ_PAGE_MENU_ITEMS"
        var menuItemsPref: SharedPreferences
        menuItemsPref = ConfigUtil.loadPreference(configFileName)
        if(menuItemsPref.getBoolean("FIRST", true)){
        }
    }
    inner class MyRecyclerViewAdapter() :
        RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_read_page_settings, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val settingItem = itemList[position]

        }

        override fun getItemCount() = 0
    }
}