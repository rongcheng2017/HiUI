package com.rongcheng.hi.ui.banner.core

/**
 * HiBanner的数据绑定接口，基于该接口可以实现数据的绑定和架构层解耦
 * */
interface IBindAdapter<T> {
    fun onBind(viewHolder: HiBannerAdapter.HiBannerViewHolder, mo: HiBannerMo, position: Int)
}