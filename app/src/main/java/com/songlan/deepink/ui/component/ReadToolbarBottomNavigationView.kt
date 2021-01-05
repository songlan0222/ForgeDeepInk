package com.songlan.deepink.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.view_read_toolbar_navigation.view.*

class ReadToolbarBottomNavigationView(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_read_toolbar_navigation, this)

        tabLayout.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.details -> {

                }
                R.id.directory -> {

                }
                R.id.more -> {

                }
            }
            true
        }
    }


}