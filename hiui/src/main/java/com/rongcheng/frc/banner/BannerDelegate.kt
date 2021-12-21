package com.rongcheng.frc.banner

import android.content.Context
import android.widget.FrameLayout


class BannerDelegate(
    context: Context, mBanner: Banner
) : IBanner {
    private val mViewPager: ViewPager = ViewPager(context)
    private val bannerAdapter = BannerAdapter()


    init {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        mViewPager.adapter = bannerAdapter
        mBanner.removeAllViews()
        mBanner.addView(mViewPager, layoutParams)
    }

    override fun setData(data: List<BannerMo>, layoutId: Int) {
        bannerAdapter.setLayoutResId(layoutId)
        bannerAdapter.setData(data)

    }

    override fun setData(data: List<BannerMo>) {
       
    }

    override fun setAutoPlay(autoPlay: Boolean) {
       
    }

    override fun setLoop(loop: Boolean) {
       
    }

    override fun setIntervalTime(intervalTime: Long) {
       
    }

    override fun setBindAdapter(bindAdapter: IBannerBindAdapter) {
    }
}