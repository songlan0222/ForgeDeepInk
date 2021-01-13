package com.songlan.deepink.ui.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.songlan.deepink.utils.ReadPageProfileUtil
import kotlinx.android.synthetic.main.activity_setting.view.*


class ReadPage : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        resize()
    }

    /**
     * 去除当前页无法显示的字
     * @return 去掉的字数
     */
    fun resize(): Int {
        val oldContent = text
        val newContent = oldContent.subSequence(0, getCharNum())
        text = newContent
        return oldContent.length - newContent.length
    }

    /**
     * 获取当前页总字数
     */
    fun getCharNum(): Int {
        return layout.getLineEnd(getLineNum())
    }

    /**
     * 获取页面内可容纳最大字数
     */
    fun getPageCharNum(content: String): Int {
        val tempText = this.text
        this.text = content
        val charNum = getCharNum()
        this.text = tempText
        return charNum
    }

    /**
     * 获取当前页总行数
     */
    fun getLineNum(): Int {
        val layout = layout
        val topOfLastLine = height - paddingTop - paddingBottom - lineHeight
        return layout.getLineForVertical(topOfLastLine)
    }

    fun setReadPageParameters(map: Map<String, Float>){
        textSize = map[ReadPageProfileUtil.LINE_MARGIN]?:14F
        textScaleX = map[ReadPageProfileUtil.LINE_MARGIN]?:0F
        setLineSpacing(10F, map[ReadPageProfileUtil.LINE_MARGIN]?:0F)
        // 添加段间距设置方法
    }


}