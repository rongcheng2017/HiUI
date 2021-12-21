package com.rongcheng.hi.ui.banner.core

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.rongcheng.hi.ui.banner.indicator.HiIndicator

interface IHiBanner {
    fun setBannerData(@LayoutRes layoutResId: Int, models: List<out HiBannerMo>)

    fun setBannerData(models: List<out HiBannerMo>)

    fun setAutoPlay(autoPlay: Boolean)

    fun setLoop(loop: Boolean)

    fun setIntervalTime(intervalTime: Long)

    fun setBindAdapter(bindAdapter: IBindAdapter<*>)

    fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener)

    fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener)

    fun setScrollDuration(duration: Int)

    fun setIndicator(hiIndicator: HiIndicator<*>)

    interface OnBannerClickListener {
        fun onBannerClick(
            viewHolder: HiBannerAdapter.HiBannerViewHolder,
            bannerMo: HiBannerMo,
            position: Int
        )
    }
}