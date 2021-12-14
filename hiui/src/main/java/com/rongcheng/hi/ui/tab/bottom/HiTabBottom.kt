package com.rongcheng.hi.ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.rongcheng.hi.ui.R
import com.rongcheng.hi.ui.tab.common.IHiTab
import java.util.*

class HiTabBottom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), IHiTab<HiTabBottomInfo<*>> {

    val tabImageView: ImageView
    val tabNameView: TextView
    val tabIconView: TextView

    var tabInfo: HiTabBottomInfo<*>? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.hi_tab_bottom, this)
        tabImageView = findViewById(R.id.iv_image)
        tabNameView = findViewById(R.id.tv_name)
        tabIconView = findViewById(R.id.tv_icon)
    }


    override fun resetHeight(height: Int) {
        val newLayoutParams = layoutParams
        newLayoutParams.height = height
        layoutParams = newLayoutParams
        tabNameView.visibility = GONE
    }

    override fun setHiTabInfo(data: HiTabBottomInfo<*>) {
        tabInfo = data
        inflateInfo(data, selected = false, init = true)
    }

    override fun onTabSelectedChange(
        index: Int,
        prevInfo: HiTabBottomInfo<*>?,
        nextInfo: HiTabBottomInfo<*>?
    ) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) return
        tabInfo?.let {
            if (prevInfo == it) {
                inflateInfo(it, selected = false, init = false)
            } else {
                inflateInfo(it, selected = true, init = false)
            }
        }
    }

    private fun inflateInfo(iTabInfo: HiTabBottomInfo<*>, selected: Boolean, init: Boolean) {
        if (iTabInfo.tabType == HiTabBottomInfo.TabType.ICON) {
            if (init) {
                tabImageView.visibility = GONE
                tabIconView.visibility = VISIBLE
                val typeface = Typeface.createFromAsset(this.context.assets, iTabInfo.iconFont)
                tabIconView.typeface = typeface
                if (iTabInfo.name.isNotEmpty()) {
                    tabNameView.text = iTabInfo.name
                }
            }
            if (selected) {
                tabIconView.text = if (iTabInfo.selectedIconName.isNullOrEmpty())
                    iTabInfo.defaultIconName else iTabInfo.selectedIconName
                iTabInfo.tintColor?.let {
                    tabNameView.setTextColor(getTextColor(it))
                    tabIconView.setTextColor(getTextColor(it))
                }
            } else {
                tabIconView.text = iTabInfo.defaultIconName
                iTabInfo.defaultColor?.let {
                    tabNameView.setTextColor(getTextColor(it))
                    tabIconView.setTextColor(getTextColor(it))
                }
            }
        } else if (iTabInfo.tabType == HiTabBottomInfo.TabType.BITMAP) {
            if (init) {
                tabImageView.visibility = VISIBLE
                tabIconView.visibility = GONE
                if (iTabInfo.name.isNotEmpty()) {
                    tabNameView.text = iTabInfo.name
                }
            }
            if (selected) {
                tabImageView.setImageBitmap(iTabInfo.selectedBitmap)
            } else {
                tabImageView.setImageBitmap(iTabInfo.defaultBitmap)
            }
        }
    }


    @ColorInt
    private fun getTextColor(color: Any): Int = if (color is String) {
        Color.parseColor(color)
    } else
        color as Int

}