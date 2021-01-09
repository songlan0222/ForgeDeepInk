package com.songlan.deepink.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.songlan.deepink.R
import kotlinx.android.synthetic.main.fragment_last_page.*
import kotlinx.android.synthetic.main.fragment_pre_page.*

class NextPageFragment : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReadPage()
    }

    private fun initReadPage(){
        nextReadPage.text = ""
    }
}