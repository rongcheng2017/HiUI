package com.rongcheng.hi.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.rongcheng.hi.library.util.HiDisplayUtil

/**
 * 下拉刷新的Overlay视图，可以重载这个类来自定义自己的Overlay
 */
abstract class HiOverView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    enum class HiRefreshState {
        /**初始化*/
        STATE_INIT,

        /**Header展示的状态*/
        STATE_VISIBLE,


        /**正在刷新*/
        STATE_REFRESH,

        /**超出可刷新距离*/
        STATE_OVER,

        /**超出刷新位置松开手后的状态*/
        STATE_OVER_RELEASE
    }

    var state: HiRefreshState = HiRefreshState.STATE_INIT

    /**触发下拉刷新所要的最小高度*/
    var mPullRefreshHeight: Int = 0

    /**最小阻尼*/
    val mMinDamp = 1.6f

    /**最大阻尼*/
    val mMaxDamp = 2.2f

    init {
        preInit()
    }

    protected fun preInit() {
        mPullRefreshHeight = HiDisplayUtil.dp2px(66f, resources).toInt()
        init()
    }

    /**初始化*/
    abstract fun init()

    abstract fun onScroll(scrollY: Int, pullRefreshHeight: Int)

    /**显示Overlay*/
    abstract fun onVisible()

    /**超过Overlay，释放就会加载*/
    abstract fun onOver()

    /**开始刷新*/
    abstract fun onRefresh()

    /**加载完成*/
    abstract fun onFinish()
}
