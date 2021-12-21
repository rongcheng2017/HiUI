package com.rongcheng.frc.banner

import androidx.annotation.LayoutRes

interface IBannerBindAdapter {
    fun onBind(viewHolder: BannerAdapter.ViewHolder, position: Int, mo: BannerMo)
}