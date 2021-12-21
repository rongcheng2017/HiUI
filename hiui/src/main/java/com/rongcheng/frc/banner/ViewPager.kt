package com.rongcheng.frc.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * 通过Handler.postDelay()来触发setCurrentItem()方法，从而实现自动滚动
 */
class ViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    private var mAutoPlay: Boolean = false
    private var mLoop: Boolean = false
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private var mIntervalTime: Long = 2000
    private var currentPosition: Int = 0
    private val mRunnable: Runnable = Runnable {
        next()
    }

    fun setAutoPlay(autoPlay: Boolean) {
        if (autoPlay == mAutoPlay) return
        this.mAutoPlay = autoPlay
        if (autoPlay) {
            start()
        } else {
            stop()
        }
    }

    fun setLoop(loop: Boolean) {
        mLoop = loop
    }

    fun setIntervalTime(intervalTime: Long) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime
        }
    }

    private fun next() {
        if ((adapter?.count ?: 0) > 0) {
            val realCount = (adapter as BannerAdapter).getRealCount()
            currentPosition++
            if (mLoop) {
                setCurrentItem(currentItem % realCount, true)
            } else if (currentPosition in 0 until realCount) {
                setCurrentItem(currentPosition, true)
            }
        }
    }

    private fun stop() {
        mHandler.removeCallbacks(mRunnable)
    }

    private fun start() {
        mHandler.removeCallbacksAndMessages(null)
        mHandler.postDelayed(mRunnable, mIntervalTime)
    }
}