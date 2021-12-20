package com.rongcheng.hi.ui.banner.core

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field

/**
 * 自动翻页的ViewPager
 */
class HiViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {
    /**
     * 翻页间隔
     */
    private var mIntervalTime: Long = 0
    private var mAutoPlay = true
    var isLayout = false

    private val mHandler = Handler(Looper.getMainLooper())
    private val mRunnable: Runnable by lazy {
        object : Runnable {
            override fun run() {
                next()
                mHandler.postDelayed(this, mIntervalTime)
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        isLayout = true
    }

    /**
     * 当用户手指触摸Banner时，自动滚动应该暂停
     */
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        //用户松开手的时候自动播放
        when (ev?.action) {
            MotionEvent.ACTION_UP -> start()
            MotionEvent.ACTION_CANCEL -> start()
            //用户触摸时暂停播放
            else -> stop()
        }
        return super.onTouchEvent(ev)
    }

    /**
     * fix bug: ViewPager和RecyclerView混用时，当RecyclerView滚动上去，直到ViewPager看不见，再滚动下来，ViewPager
     *          下次切换没有动画
     *
     * [ViewPager]在[setCurrentItemInternal]中会通过判断[mFirstLayout]看是否要显示动画，如果是第一次显示布局就不启用动画
     * 而[ViewPager]中的[mFirstLayout] 在[onAttachedToWindow]会被设置为true
     *
     * 我们可以通过重写[onAttachedToWindow],并反射修改[mFirstLayout]为false
     * */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //说明ViewPager已经创建过
        if (isLayout && adapter != null && (adapter?.count ?: 0) > 0) {
            try {
                val mScroller = ViewPager::class.java.getDeclaredField("mFirstLayout")
                mScroller.isAccessible = true
                mScroller.set(this, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        start()
    }

    /**
     * fix bug: ViewPager和RecyclerView混用时，当ViewPager滚动到一半的时候，RecyclerView滚动上去，
     *          再滚动下来，ViewPager会卡在一半
     *
     * [onDetachedFromWindow]会 mScroller.abortAnimation();
     */
    override fun onDetachedFromWindow() {
        if ((context as Activity).isFinishing) {
            super.onDetachedFromWindow()
        }
        stop()
    }

    /**
     * 设置ViewPager的滚动速度
     */
    fun setScrollDuration(duration: Int) {
        try {
            val scrollerField: Field = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            scrollerField.set(this, HiBannerScroller(context, duration))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setIntervalTime(intervalTime: Long) {
        this.mIntervalTime = intervalTime
    }

    fun setAutoPlay(autoPlay: Boolean) {
        this.mAutoPlay = autoPlay
        if (!mAutoPlay) {
            mHandler.removeCallbacks(mRunnable)
        }
    }

    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    private fun start() {
        mHandler.removeCallbacksAndMessages(null)
        if (mAutoPlay) {
            mHandler.postDelayed(mRunnable, mIntervalTime)
        }
    }

    /**
     * 设置下一个要显示的item,并返回item的pos
     * return 下一个要显示item的
     */
    private fun next(): Int {
        var nextPosition = -1
        if ((adapter?.count ?: 0) <= 1) {
            stop()
            return nextPosition
        }

        nextPosition = currentItem + 1
        if (nextPosition > (adapter?.count ?: 0)) {
            nextPosition = (adapter as HiBannerAdapter).getFirstItem()
        }
        setCurrentItem(nextPosition, true)
        return nextPosition
    }

}