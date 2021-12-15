package com.rongcheng.hi.ui.refresh

interface IHiRefresh {
    /**
     * 刷新时是否禁止滚动
     * [disableRefreshScroll]  是否禁止滚动
     */
    fun setDisableRefreshScroll(disableRefreshScroll: Boolean)

    /**
     * 刷新完成
     */
    fun refreshFinished()

    /**
     * 设置下拉刷新的监听器
     * [hiRefreshListener] 刷新监听器
     */
    fun setRefreshListener(hiRefreshListener: HiRefreshListener)

    fun setRefreshOverView(hiOverView :HiOverView)

    interface HiRefreshListener {
        fun onRefresh()
        fun enableRefresh()
    }
}