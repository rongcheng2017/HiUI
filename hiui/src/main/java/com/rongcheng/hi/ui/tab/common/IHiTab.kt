package com.rongcheng.hi.ui.tab.common

import androidx.annotation.Px

interface IHiTab<D> : OnTabSelectedListener<D> {

    fun setHiTabInfo(data: D)

    /**
     * 动态修改某个Item的高度
     */
    fun resetHeight(@Px height: Int)
}