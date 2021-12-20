package com.rongcheng.hi.ui.banner.core

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

/**
 * HiViewPager的适配器，为页面提供填充数据
 */
class HiBannerAdapter(private val mContext: Context) : PagerAdapter() {
    private var mCachedViews: SparseArray<HiBannerViewHolder> = SparseArray()
    private var mBannerClickListener: IHiBanner.OnBannerClickListener? = null
    private var mBindAdapter: IBindAdapter<*>? = null
    private var mModels: List<out HiBannerMo>? = null
    private var mAutoPlay: Boolean = true
    private var mLoop: Boolean = false
    private var mLayoutResId = -1

    override fun getCount(): Int {
        //无限轮播
        return if (mAutoPlay) Int.MAX_VALUE else {
            if (mLoop) {
                Int.MAX_VALUE
            } else {
                getBannerRealCount()
            }
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var realPosition = position
        if (getBannerRealCount() > 0) {
            realPosition = position % getBannerRealCount()
        }
        val viewHolder = mCachedViews.get(realPosition)
        if (container == viewHolder.rootView.parent) {
            container.removeView(viewHolder.rootView)
        }
        if (viewHolder.rootView.parent != null) {
            (viewHolder.rootView.parent as ViewGroup).removeView(viewHolder.rootView)
        }
        onBind(viewHolder, mModels!![realPosition], getBannerRealCount())
        container.addView(viewHolder.rootView)
        return viewHolder.rootView
    }

    override fun getItemPosition(`object`: Any): Int {
        //让Item每次都刷新
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

    protected fun onBind(viewHolder: HiBannerViewHolder, bannerMo: HiBannerMo, position: Int) {
        viewHolder.rootView.setOnClickListener {
            mBannerClickListener?.onBannerClick(viewHolder, bannerMo, position)
        }
        mBindAdapter?.onBind(viewHolder, bannerMo, position)
    }

    fun getBannerRealCount(): Int {
        return mModels?.size ?: 0
    }

    fun getFirstItem(): Int {
        return (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % getBannerRealCount())
    }


    fun setLayoutResId(@LayoutRes layoutResId: Int) {
        this.mLayoutResId = layoutResId
    }

    fun setBannerData(models: List<out HiBannerMo>) {
        this.mModels = models
        initCachedView()
        notifyDataSetChanged()
    }

    fun setBindAdapter(bindAdapter: IBindAdapter<*>) {
        this.mBindAdapter = bindAdapter
    }

    fun setOnBannerClickListener(onBannerClickListener: IHiBanner.OnBannerClickListener) {
        this.mBannerClickListener = onBannerClickListener
    }

    fun setAutoPlay(autoPlay: Boolean) {
        this.mAutoPlay = autoPlay
    }

    fun setLoop(loop: Boolean) {
        this.mLoop = loop
    }

    private fun initCachedView() {
        mCachedViews = SparseArray()
        mModels?.forEachIndexed { index, _ ->
            val viewHolder = HiBannerViewHolder(
                createView(LayoutInflater.from(mContext), null),
            )
            mCachedViews.put(index, viewHolder)
        }
    }

    private fun createView(layoutInflater: LayoutInflater, parent: ViewGroup?): View {
        if (mLayoutResId == -1) {
            throw IllegalArgumentException("you must invoke setLayoutResId() method first!")
        }
        return layoutInflater.inflate(mLayoutResId, parent, false)
    }

    class HiBannerViewHolder(val rootView: View) {
        private var viewSparseArray: SparseArray<View>? = null

        fun <V : View> findViewById(@IdRes id: Int): V {
            if (rootView !is ViewGroup) {
                return rootView as V
            }
            if (viewSparseArray == null) {
                viewSparseArray = SparseArray(1)
            }
            var childView = viewSparseArray?.get(id)
            if (childView == null) {
                childView = rootView.findViewById(id)
                viewSparseArray?.put(id, childView)
            }
            return childView as V
        }
    }
}