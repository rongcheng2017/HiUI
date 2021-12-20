package com.rongcheng.hi.ui.banner.core

import android.content.Context
import android.widget.Scroller

class HiBannerScroller(context: Context, private val mDuration: Int = 1000) : Scroller(context) {


    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }
}