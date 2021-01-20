package com.songlan.deepink.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songlan.deepink.ui.read.ReadBookActivity

open class BaseFragment(val layout: Int) : Fragment() {

    lateinit var currentActivity: ReadBookActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (activity != null) {
            currentActivity = activity as ReadBookActivity
        }
        return inflater.inflate(layout, container, false)
    }
}