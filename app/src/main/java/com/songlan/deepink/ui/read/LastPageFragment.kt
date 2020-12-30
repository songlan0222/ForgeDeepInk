package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songlan.deepink.R

class LastPageFragment : Fragment() {

    private lateinit var readBookActivity: ReadBookActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity != null) {
            readBookActivity = activity as ReadBookActivity
        }
        return inflater.inflate(R.layout.fragment_last_page, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}