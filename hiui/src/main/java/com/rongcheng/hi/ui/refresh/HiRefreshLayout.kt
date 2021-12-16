package com.rongcheng.hi.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import kotlin.math.abs

class HiRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IHiRefresh {

    private var mState: HiOverView.HiRefreshState = HiOverView.HiRefreshState.STATE_INIT
    private val mAutoScroller: AutoScroller = AutoScroller(this)
    private var mHiRefreshListener: IHiRefresh.HiRefreshListener? = null
    private var mGestureDetector: GestureDetector? = null
    protected var mHiOverView: HiOverView? = null

    private var mLastY = 0
    private var mDisableRefreshScroll: Boolean = false

    private val hiGestureDetector = object : HiGestureDetector() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (abs(distanceX) > abs(distanceY) || mHiRefreshListener != null && mHiRefreshListener?.enableRefresh() == false) {
                //横向滑动不处理
                return false
            }
            if (mDisableRefreshScroll && mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                //刷新时禁止了滚动
                return true
            }

            val head: View = getChildAt(0)
            val child: View? = HiScrollUtil.findScrollableChild(this@HiRefreshLayout)
            if (HiScrollUtil.childScrolled(child)) {
                //如果列表发生了滚动则不处理
                return false
            }
            mHiOverView?.apply {
                //没有刷新或者没有达到刷新的距离，且头部已经划出或者下拉  head.bottom < this.mPullRefreshHeight 不能包含=
                if ((mState != HiOverView.HiRefreshState.STATE_REFRESH || head.bottom < this.mPullRefreshHeight)
                    && (head.bottom > 0 || distanceY <= 0)) {
                    //还在滑动中
                    return if (mState != HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                        //根据阻尼计算速度
                        val seed = if (child?.top ?: 0 < this.mPullRefreshHeight) {
                            ((mLastY / mMinDamp).toInt())
                        } else {
                            (mLastY / mMaxDamp).toInt()
                        }
                        //如果正在刷新，则不允许在滑动的时候改变状态
                        val bool = moveDown(seed, true)
                        mLastY = (-distanceY).toInt()
                        bool
                    } else {
                        false
                    }
                } else {
                    return false
                }

            }

            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val head = getChildAt(0)
        val child = getChildAt(1)
        if (head != null && child != null) {
            val childTop = child.top
            if (mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                head.layout(
                    0,
                    mHiOverView!!.mPullRefreshHeight - head.measuredHeight,
                    right,
                    mHiOverView!!.mPullRefreshHeight
                )
                child.layout(
                    0,
                    mHiOverView!!.mPullRefreshHeight,
                    right,
                    mHiOverView!!.mPullRefreshHeight + child.measuredHeight
                )
            } else {
                head.layout(0, childTop - head.measuredHeight, right, childTop)
                child.layout(0, childTop, right, childTop + child.measuredHeight)
            }
            var other: View
            if (childCount > 2)
                for (i in 2..childCount) {
                    other = getChildAt(i)
                    other?.layout(0, top, right, bottom)
                }
        }
    }

    /**
     * 根据偏移量移动head与child
     * [offsetY] 偏移量
     * [nonAuto] 是否非自动滚动触发
     */
    private fun moveDown(offsetY: Int, nonAuto: Boolean): Boolean {
        var offsetY = offsetY
        val head: View? = getChildAt(0)
        val child: View? = getChildAt(1)
        val childTop = (child?.top ?: 0) + offsetY
        if (childTop <= 0) {//异常情况
            offsetY = if (child == null) 0 else -child.top
            //移动head与child的位置到原始位置
            head?.offsetTopAndBottom(offsetY)
            child?.offsetTopAndBottom(offsetY)
            if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {
                mState = HiOverView.HiRefreshState.STATE_INIT
            }
        } else if (mState == HiOverView.HiRefreshState.STATE_REFRESH && childTop > mHiOverView!!.mPullRefreshHeight) {
            //如果正在下拉刷新中，禁止继续下拉
            return false
        } else if (childTop <= mHiOverView!!.mPullRefreshHeight) {
            //还没超出设定的刷新距离
            if (mHiOverView!!.state != HiOverView.HiRefreshState.STATE_VISIBLE && nonAuto) {
                //头部开始显示  下拉-->头部全部显示 的过程
                mHiOverView?.onVisible()
                mHiOverView?.state = HiOverView.HiRefreshState.STATE_VISIBLE
                mState = HiOverView.HiRefreshState.STATE_VISIBLE
            }
            head?.offsetTopAndBottom(offsetY)
            child?.offsetTopAndBottom(offsetY)
            /**触发刷新*/
            if (childTop == mHiOverView!!.mPullRefreshHeight && mState == HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                refresh()
            }
        } else {
            if (mHiOverView?.state != HiOverView.HiRefreshState.STATE_OVER && nonAuto) {
                //超出刷新位置
                mHiOverView?.onOver()
                mHiOverView?.state = HiOverView.HiRefreshState.STATE_OVER
            }
            head?.offsetTopAndBottom(offsetY)
            child?.offsetTopAndBottom(offsetY)
        }
        mHiOverView?.onScroll(head?.bottom ?: 0, mHiOverView!!.mPullRefreshHeight)
        return true
    }

    private fun refresh() {
        mHiRefreshListener?.onRefresh()
        mState = HiOverView.HiRefreshState.STATE_REFRESH
        mHiOverView?.onRefresh()
        mHiOverView?.state = HiOverView.HiRefreshState.STATE_REFRESH
    }

    init {
        mGestureDetector = GestureDetector(context, hiGestureDetector)

    }

    override fun setDisableRefreshScroll(disableRefreshScroll: Boolean) {
        this.mDisableRefreshScroll = disableRefreshScroll
    }

    override fun refreshFinished() {
        val head = getChildAt(0)
        mHiOverView?.onFinish()
        mHiOverView?.state = HiOverView.HiRefreshState.STATE_INIT
        val bottom = head.bottom
        if (bottom > 0) {
            recover(bottom)
        }
        mState = HiOverView.HiRefreshState.STATE_INIT
    }

    override fun setRefreshListener(hiRefreshListener: IHiRefresh.HiRefreshListener) {
        this.mHiRefreshListener = hiRefreshListener
    }

    override fun setRefreshOverView(hiOverView: HiOverView) {
        if (mHiOverView != null) {
            removeView(mHiOverView)
        }
        this.mHiOverView = hiOverView
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(mHiOverView, 0, layoutParams)
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
                    if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {//非刷新状态,还原
                        recover(head.bottom)
                        return false
                    }
                }
                mLastY = 0
            }
            //用户一直下拉没有松手
            val consumed: Boolean? = mGestureDetector?.onTouchEvent(ev)
            if ((consumed == true ||
                        (mState != HiOverView.HiRefreshState.STATE_INIT
                                && mState != HiOverView.HiRefreshState.STATE_REFRESH))
                && head.bottom > 0
            ) {
                //让我们父类无法处理这个事件
                ev.action = MotionEvent.ACTION_CANCEL
                return super.dispatchTouchEvent(ev)
            }
            if (consumed == true) {
                return true
            }
        }
        //让父类处理
        return super.dispatchTouchEvent(ev)
    }

    private fun recover(dis: Int) {
        // dis > mHiOverView!!.mPullRefreshHeight  必须是 >  而不是>=
        if (mHiRefreshListener != null && mHiOverView != null && dis > mHiOverView!!.mPullRefreshHeight) {
            //滚动到指定位置 (在设置了刷新监听的情况下，需用开发者判断刷新是否结束，所以此处需要在固定位置显示刷新视图。不能让Overview直接消失 )
            mAutoScroller.recover(dis - mHiOverView!!.mPullRefreshHeight)
            mState = HiOverView.HiRefreshState.STATE_OVER_RELEASE
        } else mAutoScroller.recover(dis)
    }

    inner class AutoScroller(val view: View) : Runnable {
        private val mScroller: Scroller = Scroller(view.context, LinearInterpolator())
        private var mLastY = 0
        private var mIsFinished: Boolean = true

        override fun run() {
            if (mScroller.computeScrollOffset()) {//还未完成滚动
                moveDown(mLastY - mScroller.currY, false)
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