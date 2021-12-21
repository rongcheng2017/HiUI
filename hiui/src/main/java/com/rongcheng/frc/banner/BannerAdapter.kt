package com.rongcheng.frc.banner

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

class BannerAdapter : PagerAdapter() {
    private var mData: List<BannerMo>? = null

    @LayoutRes
    private var mLayoutRes: Int = -1
    override fun getCount(): Int {
        //todo 无线轮播
        return getRealCount()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    fun getRealCount(): Int {
        return mData?.size ?: 0
    }

    fun setLayoutResId(layoutId: Int) {
        mLayoutRes = layoutId

    }

    fun setData(data: List<BannerMo>) {
        mData = data
        notifyDataSetChanged()

    }


    class ViewHolder {

    }
}