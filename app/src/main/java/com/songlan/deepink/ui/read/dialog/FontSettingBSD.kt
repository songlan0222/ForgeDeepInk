package com.songlan.deepink.ui.read.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.songlan.deepink.R
import com.songlan.deepink.ui.read.ReadBookActivity


class FontSettingBSD : BaseBottomSheetDialog(R.layout.dialog_rpmenu_font_setting) {

    companion object{
        var bsd: BaseBottomSheetDialog? = null

        fun getDialog(): FontSettingBSD {
            if (bsd == null) {
                bsd = FontSettingBSD()
            }
            return bsd as FontSettingBSD
        }
    }

//    companion object {
//        private var fontSizeBSD: FontSettingBSD? = null
//
//        fun getDialog(): FontSettingBSD {
//            if (fontSizeBSD == null) {
//                fontSizeBSD = FontSettingBSD()
//            }
//            return fontSizeBSD!!
//        }
//    }

//    private lateinit var readBookActivity: ReadBookActivity
//    private val toolbarFragmentMap = hashMapOf<Int, Fragment>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
//        if (activity != null) {
//            readBookActivity = activity as ReadBookActivity
//        }
//        toolbarFragmentMap.clear()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        val view = inflater.inflate(R.layout.dialog_rpmenu_font_setting, container, false)
//        view.layoutParams = ViewGroup.LayoutParams(
//            // 与界面同宽，但是高度只有总高度的4/5
//            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        return view
//    }
}