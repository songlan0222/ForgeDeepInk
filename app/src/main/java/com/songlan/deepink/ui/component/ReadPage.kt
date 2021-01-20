package com.songlan.deepink.ui.component

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.songlan.deepink.R


class ReadPage : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style)

    private lateinit var profileMap : Map<String, Float>

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        resize()
    }

    /**
     * 去除当前页无法显示的字
     * @return 去掉的字数
     */
    private fun resize(): Int {
        val oldContent = text
        val newContent = oldContent.subSequence(0, getCharNum())
        text = newContent
        return oldContent.length - newContent.length
    }

    /**
     * 获取当前页总字数
     */
    private fun getCharNum(): Int {
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
    private fun getLineNum(): Int {
        val layout = layout
        val topOfLastLine = height - paddingTop - paddingBottom - lineHeight
        return layout.getLineForVertical(topOfLastLine)
    }

    /**
     * 设置段间距，资源占用过大，取消使用
     */
    private fun setParagraphSpacing(paragraphSpacing: Int) {
        if (!text.contains("\n")) {
            return
        }
        text = text.toString().replace("\n", "\n\r")

        var previousIndex = text.indexOf("\n\r")
        //记录每个段落开始的index，第一段没有，从第二段开始
        val nextParagraphBeginIndexes = arrayListOf<Int>()
        nextParagraphBeginIndexes.add(previousIndex)
        while (previousIndex != -1) {
            val nextIndex = text.indexOf("\n\r", previousIndex + 2)
            previousIndex = nextIndex
            if (previousIndex != -1) {
                nextParagraphBeginIndexes.add(previousIndex)
            }
        }
        // 获取行高
        val tvLineHeight = lineHeight

        //把\r替换成透明长方形（宽:1px，高：字高+段距）
        val spanString = SpannableString(text)
        val rectangle = ContextCompat.getDrawable(context, R.drawable.shape_transparent_rectangle)!!
        val density = context.resources.displayMetrics.density
        // 行高 - 行距 + 段距
        rectangle.setBounds(0, 0, 1,
            ((tvLineHeight - lineSpacingExtra * density) / 1.2 + (paragraphSpacing - lineSpacingExtra) * density).toInt())

        for (index in nextParagraphBeginIndexes) {
            // \r在String中占一个index
            spanString.setSpan(ImageSpan(rectangle),
                index + 1,
                index + 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        text = spanString
    }


}