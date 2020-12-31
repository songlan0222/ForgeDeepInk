package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
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
            readBookActivity.showBottomSheetDialog()
        }
    }
}