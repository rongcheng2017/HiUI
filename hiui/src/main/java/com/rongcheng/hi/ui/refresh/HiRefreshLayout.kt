package com.rongcheng.hi.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller

class HiRefreshLayout constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IHiRefresh {

    private var mState: HiOverView.HiRefreshState = HiOverView.HiRefreshState.STATE_INIT
    private val mAutoScroller: AutoScroller = AutoScroller(this)
    private var mHiRefreshListener: IHiRefresh.HiRefreshListener? = null
    private var mGestureDetector: GestureDetector? = null
    protected var mHiOverView: HiOverView? = null

    private var mLastY = 0;
    private var mDisableRefreshScroll: Boolean = false

    private val hiGestureDetector = object : HiGestureDetector() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }

    init {
        mGestureDetector = GestureDetector(context, hiGestureDetector)

    }

    override fun setDisableRefreshScroll(disableRefreshScroll: Boolean) {
        this.mDisableRefreshScroll = disableRefreshScroll
    }

    override fun refreshFinished() {

    }

    override fun setRefreshListener(hiRefreshListener: IHiRefresh.HiRefreshListener) {
        this.mHiRefreshListener = hiRefreshListener
    }

    override fun setRefreshOverView(hiOverView: HiOverView) {
        this.mHiOverView = hiOverView
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val head = getChildAt(0)
        ev?.apply {
            if (action == MotionEvent.ACTION_CANCEL ||
                action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_INDEX_MASK
            ) {
                //用户松开手
                if (head.bottom > 0) {
                    if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {
                        recover(head.bottom)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun recover(dis: Int) {
        if (mHiRefreshListener != null && mHiOverView != null && dis > mHiOverView!!.mPullRefreshHeight) {
            //滚动到指定位置
            mAutoScroller.recover(dis - mHiOverView!!.mPullRefreshHeight)
            mState = HiOverView.HiRefreshState.STATE_OVER_RELEASE
        } else mAutoScroller.recover(dis)
    }

    class AutoScroller(val view: View) : Runnable {
        private val mScroller: Scroller = Scroller(view.context, LinearInterpolator())
        private var mLastY = 0
        var mIsFinished: Boolean = true
            private set

        override fun run() {
            if (mScroller.computeScrollOffset()) {//还未完成滚动
                mLastY = mScroller.currY
                view.post(this)
            } else {
                view.removeCallbacks(this)
                mIsFinished = true
            }
        }

        fun recover(dis: Int) {
            if (dis < 0) {
                return
            }
            view.removeCallbacks(this)
            mLastY = 0
            mIsFinished = false
            mScroller.startScroll(0, 0, 0, dis, 300)
            view.post(this)
        }

    }
}