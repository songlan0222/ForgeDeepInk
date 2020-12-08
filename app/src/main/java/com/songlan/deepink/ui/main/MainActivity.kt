package com.songlan.deepink.ui.main

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.songlan.deepink.R
import com.songlan.deepink.ui.main.`interface`.BackHandleInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BackHandleInterface {

    private lateinit var backHandleFragment: BaseFragment
    val vm by lazy {
        ViewModelProvider(this).get(MainActivityVM::class.java)
    }
    private val fragmentMap = hashMapOf<Int, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
        main_viewpager.currentItem = MainActivityVM.DEFAULT_ITEM_ID
    }

    public fun changeFragment(id: Int){
        main_viewpager.setCurrentItem(id, true)
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int = 3

        override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> fragmentMap[0] ?: BookshelfGroupsFragment()
                1 -> fragmentMap[1] ?: BookshelfDetailsFragment()
                else -> BookshelfOthersFragment()
            }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            fragmentMap[position] = fragment
            return fragment
        }

    }

    // 添加返回事件
    override fun onSelectedFragment(backHandleFragment: BaseFragment) {
        this.backHandleFragment = backHandleFragment
    }

    override fun onBackPressed() {
        if(backHandleFragment == null || !backHandleFragment.onBackPressed()){
            if(supportFragmentManager.backStackEntryCount == 0){
                super.onBackPressed();
            }else{
                supportFragmentManager.popBackStack();
            }
        }
    }


}