package com.songlan.deepink.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.max

class AutoLineFeedLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        var curLineWidth = 0
        var curLineTop = 0 //curLineWidth 累加item布局时的x轴偏移curLineTop 累加item布局时的x轴偏移
        var lastLineMaxHeight = 0
        for (i in 0 until itemCount) {
            val view: View = recycler.getViewForPosition(i)
            //获取每个item的布局参数，计算每个item的占用位置时需要加上margin
            val params = view.layoutParams as RecyclerView.LayoutParams
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin
            val height = getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin
            curLineWidth += width //累加当前行已有item的宽度
            if (curLineWidth > getWidth() || i == 3) {

                //换行
                curLineWidth = width
                if (lastLineMaxHeight == 0) {
                    lastLineMaxHeight = height
                }
                //记录当前行top
                curLineTop += lastLineMaxHeight
                layoutDecorated(
                    view,
                    params.leftMargin,
                    curLineTop + params.topMargin,
                    width - params.rightMargin,
                    curLineTop + height - params.bottomMargin
                )
                lastLineMaxHeight = height

            } else {
                //如果累加的宽度小于等于RecyclerView的宽度，不需要换行
                layoutDecorated(
                    view,
                    curLineWidth - width + params.leftMargin,
                    curLineTop + params.topMargin,
                    curLineWidth - params.rightMargin,
                    curLineTop + height - params.bottomMargin
                ) //布局item的真实位置
                //比较当前行多有item的最大高度，用于换行后计算item在y轴上的偏移量
                lastLineMaxHeight = max(lastLineMaxHeight, height)
            }
        }
    }

    init {
        isAutoMeasureEnabled = true //layoutmanager必须调用此方法设为true才能在onMesure时自动布局
    }
}