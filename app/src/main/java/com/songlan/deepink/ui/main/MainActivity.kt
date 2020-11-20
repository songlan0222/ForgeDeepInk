package com.songlan.deepink.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val vm by lazy {
        ViewModelProvider(this).get(MainActivityVM::class.java)
    }
    private val fragmentMap = hashMapOf<Int, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
        main_viewpager.currentItem = vm.DEFAULT_ITEM_ID
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int = 3

        override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> fragmentMap[0] ?: LeftFragment()
                1 -> fragmentMap[1] ?: CenterFragment()
                else -> RightFragment()
            }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            fragmentMap[position] = fragment
            return fragment
        }

    }


}