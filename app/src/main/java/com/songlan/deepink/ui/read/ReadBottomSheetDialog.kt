package com.songlan.deepink.ui.read

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.songlan.deepink.R
import com.songlan.deepink.utils.LogUtils
import kotlinx.android.synthetic.main.dialog_reading_tool_bar.*


class ReadBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        private var readBottomSheetDialog: ReadBottomSheetDialog? = null

        fun getDialog(): ReadBottomSheetDialog {
            if (readBottomSheetDialog == null) {
                readBottomSheetDialog = ReadBottomSheetDialog()
            }
            return readBottomSheetDialog!!
        }
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
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.dialog_reading_tool_bar, container, false)
        view.layoutParams = ViewGroup.LayoutParams(
            // 与界面同宽，但是高度只有总高度的3/4
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

        // 配置导航栏
        val detailsBtn = rootView.findViewById<TextView>(R.id.detailsBtn)
        val directoryBtn = rootView.findViewById<TextView>(R.id.directoryBtn)
        val moreBtn = rootView.findViewById<TextView>(R.id.moreBtn)
        detailsBtn.setOnClickListener {
            detailsBtn.isSelected = true
            directoryBtn.isSelected = false
            moreBtn.isSelected = false
            changePageFragment(PAGE_DETAILS)
        }
        directoryBtn.setOnClickListener {
            detailsBtn.isSelected = false
            directoryBtn.isSelected = true
            moreBtn.isSelected = false
            changePageFragment(PAGE_DIRECTORY)
        }
        moreBtn.setOnClickListener {
            detailsBtn.isSelected = false
            directoryBtn.isSelected = false
            moreBtn.isSelected = true
            changePageFragment(PAGE_MORE)
        }
        // 初始化时，设置目录页面为选中页面
        directoryBtn.isSelected = true

    }

    override fun onStart() {
        super.onStart()
        // 拿到系统的 bottom_sheet
        val view = dialog?.findViewById<FrameLayout>(R.id.design_bottom_sheet)!!
        val behavior = BottomSheetBehavior.from(view)
        behavior.peekHeight = 2 * getQuarterWindowHeight()
        val layoutParams = read_toolbar_guide.layoutParams as LinearLayout.LayoutParams
        layoutParams.bottomMargin = 1 * getQuarterWindowHeight()
        read_toolbar_guide.layoutParams = layoutParams

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    // 如果隐藏状态，则销毁readBottomSheetDialog
                    readBottomSheetDialog?.dismiss()
                    readBottomSheetDialog = null
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val layoutParams = read_toolbar_guide.layoutParams as LinearLayout.LayoutParams
                val offset = (getQuarterWindowHeight() * 1 * (1 - slideOffset)).toInt()
                layoutParams.bottomMargin = offset
                read_toolbar_guide.layoutParams = layoutParams
            }
        })
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if(!this.isAdded)
            super.show(manager, tag)
    }

    private val PAGE_DETAILS = 0
    private val PAGE_DIRECTORY = 1
    private val PAGE_MORE = 2

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


    private fun changePageFragment(pageNum: Int) {
        toolbarViewPager.currentItem = pageNum
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

}