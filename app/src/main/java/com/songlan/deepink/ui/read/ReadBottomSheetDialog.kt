package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.songlan.deepink.MyApplication
import com.songlan.deepink.R

class ReadBottomSheetDialog : BottomSheetDialogFragment() {


    val toolbarFragmentMap = hashMapOf<Int, Fragment>()

    companion object {
        fun getReadBottomSheetDialog(): ReadBottomSheetDialog {
            val args = Bundle()
            val fragment = ReadBottomSheetDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_reading_tool_bar, container, false)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getThreeQuarterWindowHeight()
        )

        // 配置工具栏显示内容
        val toolbarViewPager = view.findViewById<ViewPager>(R.id.toolbarViewPager)

        toolbarViewPager.adapter = ToolbarPageViewAdapter(this.childFragmentManager)
        toolbarViewPager.currentItem = 1

        return view
    }

    private fun getThreeQuarterWindowHeight(): Int {
        val res = resources
        val displayMatrix = res.displayMetrics
        val heightPixels = displayMatrix.heightPixels
        return heightPixels - heightPixels / 4
    }

    private fun getQuarterWindowHeight(): Int {
        val res = resources
        val displayMatrix = res.displayMetrics
        val heightPixels = displayMatrix.heightPixels
        return heightPixels / 4
    }

    inner class ToolbarPageViewAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = 3

        override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> toolbarFragmentMap[0] ?: BookDetailsFragment()
                1 -> toolbarFragmentMap[1] ?: BookDirectoryFragment()
                else -> toolbarFragmentMap[2] ?: BookMoreFragment()
            }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            toolbarFragmentMap[position] = fragment
            return fragment
        }
    }
}