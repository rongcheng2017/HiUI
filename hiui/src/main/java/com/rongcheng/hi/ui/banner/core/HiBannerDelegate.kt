package com.rongcheng.hi.ui.banner.core

import android.content.Context
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.rongcheng.hi.ui.R
import com.rongcheng.hi.ui.banner.indicator.HiCircleIndicator
import com.rongcheng.hi.ui.banner.indicator.HiIndicator

/**
 * HiBanner的委托类
 * 辅助HiBanner完成各种功能得控制
 */
class HiBannerDelegate(private val mContext: Context, private val mBanner: HiBanner) : IHiBanner,
    ViewPager.OnPageChangeListener {
    private var mAdapter: HiBannerAdapter? = null
    private var mHiIndicator: HiIndicator<*>? = null
    private var mModels: List<out HiBannerMo>? = null
    private var mAutoPlay: Boolean = true
    private var mLoop: Boolean = false
    private var mHiViewPager: HiViewPager? = null
    private var mOnPageChangeListener: ViewPager.OnPageChangeListener? = null
    private var mIntervalTime = 5000L
    private var mOnBannerClickListener: IHiBanner.OnBannerClickListener? = null
    private var mScrollDuration = -1
    override fun setBannerData(layoutResId: Int, models: List<out HiBannerMo>) {
        mModels = models
        init(layoutResId)
    }


    override fun setBannerData(models: List<out HiBannerMo>) {
        setBannerData(R.layout.hi_banner_item_image, models)
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        this.mAutoPlay = autoPlay
        mAdapter?.setAutoPlay(mAutoPlay)
        mHiViewPager?.setAutoPlay(mAutoPlay)
    }

    override fun setLoop(loop: Boolean) {
        this.mLoop = loop
    }

    override fun setIntervalTime(intervalTime: Long) {
        if (intervalTime > 0)
            this.mIntervalTime = intervalTime
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter<*>) {
        mAdapter?.setBindAdapter(bindAdapter)
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener
    }

    override fun setOnBannerClickListener(onBannerClickListener: IHiBanner.OnBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener
    }

    override fun setScrollDuration(duration: Int) {
        this.mScrollDuration = duration
        if (mHiViewPager != null && duration > 0) mHiViewPager?.setScrollDuration(duration)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mOnPageChangeListener != null && mAdapter != null && mAdapter!!.getBannerRealCount() != 0) {
            mOnPageChangeListener?.onPageScrolled(
                position % mAdapter!!.getBannerRealCount(),
                positionOffset,
                positionOffsetPixels
            )
        }
    }

    override fun onPageSelected(position: Int) {
        if (mAdapter?.getBannerRealCount() == 0) return
        val realPosition = position % mAdapter!!.getBannerRealCount()
        mOnPageChangeListener?.onPageSelected(realPosition)
        mHiIndicator?.onPointChange(realPosition, mAdapter?.getBannerRealCount() ?: 0)

    }

    override fun onPageScrollStateChanged(state: Int) {
        mOnPageChangeListener?.onPageScrollStateChanged(state)
    }

    private fun init(layoutResId: Int) {
        if (mAdapter == null) {
            mAdapter = HiBannerAdapter(mContext)
        }
        if (mHiIndicator == null) {
            mHiIndicator = HiCircleIndicator(mContext)
        }
        mHiIndicator?.onInflate(mModels?.size ?: 0)

        mAdapter?.apply {
            setLayoutResId(layoutResId)
            setAutoPlay(mAutoPlay)
            setLoop(mLoop)
            mModels?.let {
                setBannerData(it)
            }
            mOnBannerClickListener?.let {
                setOnBannerClickListener(it)
            }
        }

        mHiViewPager = HiViewPager(mContext)
        mHiViewPager?.apply {
            setIntervalTime(mIntervalTime)
            addOnPageChangeListener(this@HiBannerDelegate)
            setAutoPlay(mAutoPlay)
            adapter = mAdapter
            if (mScrollDuration > 0) setScrollDuration(mScrollDuration)
        }

        if ((mLoop || mAutoPlay) && mAdapter != null && mAdapter!!.getBannerRealCount() != 0) {
            //实现无限轮播的关键点：使第一张能反向滑动到最后一张，以达到无线滚动的效果
            val firstItem = mAdapter!!.getFirstItem()
            mHiViewPager?.setCurrentItem(firstItem, false)
        }

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        mBanner.removeAllViews()
        mBanner.addView(mHiViewPager, layoutParams)
        mBanner.addView(mHiIndicator?.get(), layoutParams)

    }
}