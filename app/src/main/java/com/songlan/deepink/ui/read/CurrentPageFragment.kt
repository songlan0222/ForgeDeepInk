package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_current_page.*

class CurrentPageFragment : Fragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readPage.setOnClickListener {
            val view = View.inflate(readBookActivity, R.layout.dialog_reading_tool_bar, null)
            val dialog = BottomSheetDialog(readBookActivity)
            dialog.setContentView(view)
            val parentView = view.parent as View
            val behavior = BottomSheetBehavior.from(parentView)
            behavior.peekHeight = 730
            parentView.setBackgroundColor(
                ContextCompat.getColor(
                    readBookActivity,
                    R.color.transparent
                )
            )
        }
    }

}