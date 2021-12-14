package com.rongcheng.hi.ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.rongcheng.hi.library.util.HiDisplayUtil
import com.rongcheng.hi.library.util.HiViewUtil
import com.rongcheng.hi.ui.R
import com.rongcheng.hi.ui.tab.common.IHiTabLayout
import com.rongcheng.hi.ui.tab.common.OnTabSelectedListener
import java.util.ArrayList

class HiTabBottomLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IHiTabLayout<HiTabBottom, HiTabBottomInfo<*>> {


    private val tabSelectedChangeListeners =
        ArrayList<OnTabSelectedListener<HiTabBottomInfo<*>>>()
    private var selectedInfo: HiTabBottomInfo<*>? = null
    var bottomAlpha = 1f
    var tabBottomHeight = 50f

    var bottomLineHeight = 0.5f
    var bottomLineColor = "#dfe0e1"
    private var infoList: List<HiTabBottomInfo<*>>? = null

    private val TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM"
    override fun findTab(data: HiTabBottomInfo<*>): HiTabBottom? {
        val ll = findViewWithTag<ViewGroup>(TAG_TAB_BOTTOM)
        for (i in 0 until ll.childCount) {
            val child = ll.getChildAt(i)
            if (child is HiTabBottom) {
                if (child.tabInfo == data) {
                    return child
                }
            }
        }
        return null

    }

    override fun addTabSelectedChangeListener(listener: OnTabSelectedListener<HiTabBottomInfo<*>>) {
        tabSelectedChangeListeners.add(listener)
    }

    override fun defaultSelected(defaultInfo: HiTabBottomInfo<*>) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<HiTabBottomInfo<*>>) {
        if (infoList.isEmpty()) return
        this.infoList = infoList
        //移除之前已经添加的View
        for (i in childCount - 1 downTo 1) {
            removeViewAt(i)
        }
        selectedInfo = null
        addBackground()
        val iterator = tabSelectedChangeListeners.iterator()
        while (iterator.hasNext()) {
            if (iterator.next() is HiTabBottom) {
                iterator.remove()
            }
        }
        val ll = FrameLayout(context)
        ll.tag = TAG_TAB_BOTTOM
        val height = HiDisplayUtil.dp2px(tabBottomHeight, resources).toInt()
        val width = HiDisplayUtil.getDisplayWidthInPx(context) / infoList.size
        for (i in infoList.indices) {
            val info = infoList[i]
            val params = LayoutParams(width, height)
            params.gravity = Gravity.BOTTOM
            params.leftMargin = i * width
            val tabBottom = HiTabBottom(context)
            tabSelectedChangeListeners.add(tabBottom)
            tabBottom.setHiTabInfo(info)
            ll.addView(tabBottom, params)
            tabBottom.setOnClickListener {
                onSelected(info)
            }
        }
        val flParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        flParams.gravity = Gravity.BOTTOM
        addBottomLine()
        addView(ll, flParams)
        fixContentView()
    }

    private fun addBottomLine() {
        val bottomLine = View(context)
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor))
        val bottomLineParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            HiDisplayUtil.dp2px(bottomLineHeight, resources).toInt()
        )
        bottomLineParams.gravity = Gravity.BOTTOM
        bottomLineParams.bottomMargin =
            HiDisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, resources).toInt()
        addView(bottomLine, bottomLineParams)
        bottomLine.alpha = bottomAlpha
    }

    private fun onSelected(nextInfo: HiTabBottomInfo<*>) {
        tabSelectedChangeListeners.forEach {
            it.onTabSelectedChange(
                infoList!!.indexOf(nextInfo),
                selectedInfo,
                nextInfo = nextInfo
            )
        }
        this.selectedInfo = nextInfo
    }

    private fun addBackground() {
        val view = LayoutInflater.from(context).inflate(R.layout.hi_bottom_layout_bg, null)
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            HiDisplayUtil.dp2px(tabBottomHeight, resources).toInt()
        )
        params.gravity = Gravity.BOTTOM
        addView(view, params)
        view.alpha = bottomAlpha
    }


    private fun fixContentView() {
        if (getChildAt(0) !is ViewGroup) return
        val rootView = getChildAt(0) as ViewGroup
        var targetView: ViewGroup? = HiViewUtil.findTypeView(rootView, RecyclerView::class.java)
        if (targetView == null)
            targetView = HiViewUtil.findTypeView(rootView, ScrollView::class.java)
        if (targetView == null)
            targetView = HiViewUtil.findTypeView(rootView, AbsListView::class.java)
        targetView?.setPadding(0, 0, 0, HiDisplayUtil.dp2px(tabBottomHeight, resources).toInt())
        targetView?.clipToPadding=false
    }
}