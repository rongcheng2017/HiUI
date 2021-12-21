package com.rongcheng.hi.ui.banner.core

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.rongcheng.hi.ui.R
import com.rongcheng.hi.ui.banner.indicator.HiIndicator

class HiBanner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), IHiBanner {

    private var delegate: HiBannerDelegate? = HiBannerDelegate(context, this)

    init {
        initCustomAttrs(context, attrs)
    }

    private fun initCustomAttrs(context: Context, attrs: AttributeSet? = null) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HiBanner)
        val autoPlay = typedArray.getBoolean(R.styleable.HiBanner_autoPlay, true)
        val loop = typedArray.getBoolean(R.styleable.HiBanner_loop, true)
        val intervalTime = typedArray.getInt(R.styleable.HiBanner_intervalTime, -1)
        setAutoPlay(autoPlay)
        setLoop(loop)
        setIntervalTime(intervalTime.toLong())
        typedArray.recycle()
    }

    override fun setBannerData(layoutResId: Int, models: List<out HiBannerMo>) {
        delegate?.setBannerData(layoutResId, models)
    }

    override fun setBannerData(models: List<out HiBannerMo>) {
        delegate?.setBannerData(models)
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        delegate?.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        delegate?.setLoop(loop)
    }

    override fun setIntervalTime(intervalTime: Long) {
        delegate?.setIntervalTime(intervalTime)
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter<*>) {
        delegate?.setBindAdapter(bindAdapter)
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        delegate?.setOnPageChangeListener(onPageChangeListener)
    }

    override fun setOnBannerClickListener(onBannerClickListener: IHiBanner.OnBannerClickListener) {
        delegate?.setOnBannerClickListener(onBannerClickListener)
    }

    override fun setScrollDuration(duration: Int) {
        delegate?.setScrollDuration(duration)
    }

    override fun setIndicator(hiIndicator: HiIndicator<*>) {
        delegate?.setIndicator(hiIndicator)
    }
}