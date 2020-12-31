package com.songlan.deepink.ui.read

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_current_page.*

class CurPageFragment : Fragment() {

    private lateinit var readBookActivity: ReadBookActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
        }
        return inflater.inflate(R.layout.fragment_current_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        readPage.setOnClickListener {
            val view = View.inflate(readBookActivity, R.layout.dialog_reading_tool_bar, null)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getThreeQuarterWindowHeight()
            )
            val dialog = BottomSheetDialog(readBookActivity)
            dialog.setContentView(view)
            val parentView = view.parent as View
            val behavior = BottomSheetBehavior.from(parentView)
            behavior.peekHeight = getQuarterWindowHeight()
            parentView.setBackgroundColor(
                ContextCompat.getColor(
                    readBookActivity,
                    R.color.transparent
                )
            )
            dialog.show()

            // 配置工具栏显示内容
//            val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
//            viewPager.adapter = ToolbarPageViewAdapter(supportFragmentManager)
        }
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

}