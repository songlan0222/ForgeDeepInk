package com.songlan.deepink.ui.main.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.songlan.deepink.ui.`interface`.BackHandleInterface


public abstract class BaseFragment : Fragment() {

    private lateinit var backHandleInterface: BackHandleInterface

    public abstract fun onBackPressed(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 获取带有重写返回功能的activity
        if (activity != null && activity is BackHandleInterface) {
            backHandleInterface = activity as BackHandleInterface
        } else {
            throw ClassCastException("Hosting Activity must implement BackHandledInterface")
        }
    }

    override fun onStart() {
        super.onStart()
        // 将当前Fragment与Activity绑定
        backHandleInterface.onSelectedFragment(this)
    }


}