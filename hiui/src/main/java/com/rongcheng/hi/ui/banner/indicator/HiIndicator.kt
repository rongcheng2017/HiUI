package com.rongcheng.hi.ui.banner.indicator

import android.view.View

/**
 * 指示器统一接口
 */
interface HiIndicator<T : View> {
    fun get(): T

    /**
     * 初始化Indicator
     * [count] 幻灯片数量
     */
    fun onInflate(count: Int)

    /**
     * 幻灯片切换回调
     * [current] 切换到的幻灯片位置
     * [count] 幻灯片数量
     */
    fun onPointChange(current: Int, count: Int)

}