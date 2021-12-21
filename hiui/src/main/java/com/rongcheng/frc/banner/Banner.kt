package com.rongcheng.frc.banner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.rongcheng.hi.ui.R

class Banner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), IBanner {


    private val mDelegate = BannerDelegate(context,this)


    override fun setData(data: List<BannerMo>, layoutId: Int) {

    }

    override fun setData(data: List<BannerMo>) {
        setData(data, R.layout.hi_banner_item_image)
    }


    override fun setAutoPlay(autoPlay: Boolean) {
        mDelegate.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        mDelegate.setLoop(loop)
    }

    override fun setIntervalTime(intervalTime: Long) {
        mDelegate.setIntervalTime(intervalTime)
    }

    override fun setBindAdapter(bindAdapter: IBannerBindAdapter) {
        mDelegate.setBindAdapter(bindAdapter)
    }


}