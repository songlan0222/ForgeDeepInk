package com.songlan.deepink.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class  BaseRecyclerViewAdapter: RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

    }
}