package com.rongcheng.frc.banner

import androidx.annotation.LayoutRes

interface IBanner {

    fun setData(data: List<BannerMo>, @LayoutRes layoutId: Int)

    fun setData(data: List<BannerMo>)

    fun setAutoPlay(autoPlay: Boolean)


    fun setLoop(loop: Boolean)

    fun setIntervalTime(intervalTime: Long)


    fun setBindAdapter(bindAdapter: IBannerBindAdapter)


}