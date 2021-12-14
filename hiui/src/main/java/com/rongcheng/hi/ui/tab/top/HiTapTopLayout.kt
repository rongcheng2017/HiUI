package com.rongcheng.hi.ui.tab.top

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.rongcheng.hi.library.util.HiDisplayUtil
import com.rongcheng.hi.ui.tab.common.IHiTabLayout
import com.rongcheng.hi.ui.tab.common.OnTabSelectedListener
import java.util.ArrayList
import kotlin.math.abs

class HiTapTopLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
) : HorizontalScrollView(
    context,
    attributeSet,
    defStyleAttr
), IHiTabLayout<HiTabTop, HiTabTopInfo<*>> {
    private val tabSelectedChangeListeners =
        ArrayList<OnTabSelectedListener<HiTabTopInfo<*>>>()
    private var infoList: List<HiTabTopInfo<*>>? = null
    private var selectedInfo: HiTabTopInfo<*>? = null
    override fun findTab(data: HiTabTopInfo<*>): HiTabTop? {
        val ll = getRootLayout(false)
        for (i in 0 until ll.childCount) {
            val child = ll.getChildAt(i)
            if (child is HiTabTop) {
                if (child.tabInfo == data) {
                    return child
                }
            }
        }
        return null
    }

    init {
        isVerticalScrollBarEnabled = false
    }

    override fun addTabSelectedChangeListener(listener: OnTabSelectedListener<HiTabTopInfo<*>>) {
        tabSelectedChangeListeners.add(listener)
    }

    override fun defaultSelected(defaultInfo: HiTabTopInfo<*>) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<HiTabTopInfo<*>>) {
        if (infoList.isEmpty()) return
        this.infoList = infoList
        //移除之前已经添加的View
        for (i in childCount - 1 downTo 1) {
            removeViewAt(i)
        }
        selectedInfo = null
        val linearLayout = getRootLayout(true)
        val iterator = tabSelectedChangeListeners.iterator()
        while (iterator.hasNext()) {
            if (iterator.next() is HiTabTop) {
                iterator.remove()
            }
        }
        for (i in infoList.indices) {
            val info = infoList[i]
            val tabTop = HiTabTop(context)
            tabSelectedChangeListeners.add(tabTop)
            tabTop.setHiTabInfo(info)
            linearLayout.addView(tabTop)
            tabTop.setOnClickListener {
                onSelected(info)
            }
        }
    }


    private fun getRootLayout(clear: Boolean): LinearLayout {
        var rootView = getChildAt(0)
        if (rootView == null) {
            rootView = LinearLayout(context)
            rootView.orientation = LinearLayout.HORIZONTAL
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            addView(rootView, params)
        } else if (clear) {
            (rootView as LinearLayout).removeAllViews()
        }

        return rootView as LinearLayout
    }

    private fun onSelected(nextInfo: HiTabTopInfo<*>) {
        tabSelectedChangeListeners.forEach {
            it.onTabSelectedChange(
                infoList!!.indexOf(nextInfo),
                selectedInfo,
                nextInfo = nextInfo
            )
        }
        this.selectedInfo = nextInfo
        autoScroll(nextInfo)
    }

    var tabWidth: Int = 0
    private fun autoScroll(nextInfo: HiTabTopInfo<*>) {
        val tabTop = findTab(nextInfo) ?: return
        val index = infoList!!.indexOf(nextInfo)
        val loc: IntArray = IntArray(2)
        tabTop.getLocationInWindow(loc)
        if (tabWidth == 0) {
            tabWidth = tabTop.width
        }
        var scrollWidth = 0
        //判断点击了屏幕左侧还是右侧
        scrollWidth =
            if ((loc[0] + tabWidth / 2) > HiDisplayUtil.getDisplayWidthInPx(context) / 2) {
                rangeScrollWidth(index, 2)
            } else {
                rangeScrollWidth(index, -2)
            }
        scrollTo(scrollX + scrollWidth, 0)
    }

    /**
     * 获取可滚动范围
     * [index] 从第几个开始
     * [range] 向前向后的范围
     */
    private fun rangeScrollWidth(index: Int, range: Int): Int {
        var scrollWidth = 0
        for (i in 0..abs(range)) {
            var next = 0
            next = if (range < 0)
                range + i + index
            else
                range - i + index
            if (next >= 0 && next < infoList!!.size) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false)
                } else {
                    scrollWidth += scrollWidth(next, true)
                }
            }
        }
        return scrollWidth
    }

    /**
     * 指定位置的控件可滚动的距离
     * [index] 指定位置的控件
     * [toRight] 是否是点击了屏幕右侧
     * return 可滚动距离
     */
    private fun scrollWidth(index: Int, toRight: Boolean): Int {
        val target = findTab(infoList!![index]) ?: return 0
        val rect = Rect()
        target.getLocalVisibleRect(rect)
        if (toRight)//点击屏幕右侧
        {
            return if (rect.right > tabWidth) {//right坐标大于控件的宽度时，说明完全没有显示
                tabWidth
            } else { //显示部分，减去已经显示的宽度
                tabWidth - rect.right
            }
        } else {
            if (rect.left <= -tabWidth)//left坐标小于等于控件的宽度，说明完全没有显示
                return tabWidth
            else if (rect.left > 0)//显示部分
                return rect.left
            return 0
        }

    }
}