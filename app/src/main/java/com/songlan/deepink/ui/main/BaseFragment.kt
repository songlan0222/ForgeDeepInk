package com.songlan.deepink.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.songlan.deepink.ui.main.`interface`.BackHandleInterface


public abstract class BaseFragment : Fragment() {

    private lateinit var backHandleInterface: BackHandleInterface

    public abstract fun onBackPressed(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is BackHandleInterface) {
            backHandleInterface = (activity as BackHandleInterface?)!!
        } else {
            throw ClassCastException("Hosting Activity must implement BackHandledInterface")
        }
    }

    override fun onStart() {
        super.onStart()
        backHandleInterface.onSelectedFragment(this)
    }


}