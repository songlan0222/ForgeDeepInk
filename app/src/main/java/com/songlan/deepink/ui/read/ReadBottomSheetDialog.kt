package com.songlan.deepink.ui.read

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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

    companion object {
        private val readBottomSheetDialog: ReadBottomSheetDialog? = null

        fun getDialog() = ReadBottomSheetDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_reading_tool_bar, null)
        dialog.setContentView(view)
        initView(view)
        return dialog
    }

    private fun initView(rootView: View) {


    }


}