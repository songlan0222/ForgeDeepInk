package com.songlan.deepink.ui.read

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.songlan.deepink.R
import com.songlan.deepink.utils.LogUtils


class ReadBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        private val readBottomSheetDialog: ReadBottomSheetDialog? = null

        fun getDialog() = ReadBottomSheetDialog()
    }

    private lateinit var readBookActivity: ReadBookActivity
    private val toolbarFragmentMap = hashMapOf<Int, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
        }
        toolbarFragmentMap.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_reading_tool_bar, container, false)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, getThreeQuarterWindowHeight()
        )

        initView(view)
        return view
    }

    private fun initView(rootView: View) {
        // 配置工具栏显示内容
        val toolbarViewPager = rootView.findViewById<ViewPager>(R.id.toolbarViewPager)
        toolbarViewPager.adapter = ToolbarPageViewAdapter(this.childFragmentManager)
        toolbarViewPager.currentItem = 1
    }

    override fun onStart() {
        super.onStart()
        // 拿到系统的 bottom_sheet
        val view = dialog?.findViewById<FrameLayout>(R.id.design_bottom_sheet)!!
        val behavior = BottomSheetBehavior.from(view)
        behavior.peekHeight = getQuarterWindowHeight()
    }


    inner class ToolbarPageViewAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = 3

        override fun getItem(position: Int): Fragment {
            LogUtils.v("MainTest", "获取页面是：$position ~~~")
            return when (position) {
                0 -> toolbarFragmentMap[0] ?: BookDetailsFragment()
                1 -> toolbarFragmentMap[1] ?: BookDirectoryFragment()
                else -> BookMoreFragment()
            }
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            toolbarFragmentMap[position] = fragment
            LogUtils.v("MainTest", "获取页面是：~~~ ${fragment.toString()}")
            return fragment
        }

        override fun saveState(): Parcelable? {
            return null
        }
    }

    private fun getQuarterWindowHeight(): Int {
        val res = resources
        val displayMatrix = res.displayMetrics
        val heightPixels = displayMatrix.heightPixels
        return heightPixels / 4
    }

    private fun getThreeQuarterWindowHeight(): Int {
        val res = resources
        val displayMatrix = res.displayMetrics
        val heightPixels = displayMatrix.heightPixels
        return heightPixels - heightPixels / 4
    }

//    override fun show(manager: FragmentManager, tag: String?) {
//        try{
//            manager.beginTransaction().remove(this).commit()
//            super.show(manager, tag)
//        } catch (e: Exception){
//            e.printStackTrace()
//        }
//    }

}