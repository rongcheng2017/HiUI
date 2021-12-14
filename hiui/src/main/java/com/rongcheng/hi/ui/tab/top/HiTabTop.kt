package com.rongcheng.hi.ui.tab.top

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.rongcheng.hi.ui.R
import com.rongcheng.hi.ui.tab.common.IHiTab
import java.util.*

class HiTabTop @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), IHiTab<HiTabTopInfo<*>> {

    val tabImageView: ImageView
    val tabNameView: TextView
    val indicator: View
    var tabInfo: HiTabTopInfo<*>? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.hi_tab_top, this)
        tabImageView = findViewById(R.id.iv_image)
        tabNameView = findViewById(R.id.tv_name)
        indicator = findViewById(R.id.tab_top_indicator)
    }


    override fun resetHeight(height: Int) {
        val newLayoutParams = layoutParams
        newLayoutParams.height = height
        layoutParams = newLayoutParams
        tabNameView.visibility = GONE
    }

    override fun setHiTabInfo(data: HiTabTopInfo<*>) {
        tabInfo = data
        inflateInfo(data, selected = false, init = true)
    }

    override fun onTabSelectedChange(
        index: Int,
        prevInfo: HiTabTopInfo<*>?,
        nextInfo: HiTabTopInfo<*>?
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

    private fun inflateInfo(iTabInfo: HiTabTopInfo<*>, selected: Boolean, init: Boolean) {
        if (iTabInfo.tabType == HiTabTopInfo.TabType.TEXT) {
            if (init) {
                tabImageView.visibility = GONE
                tabNameView.visibility = VISIBLE
                if (iTabInfo.name.isNotEmpty()) {
                    tabNameView.text = iTabInfo.name
                }
            }
            if (selected) {
                iTabInfo.tintColor?.let {
                    tabNameView.setTextColor(getTextColor(it))
                    indicator.visibility = VISIBLE
                }
            } else {
                iTabInfo.defaultColor?.let {
                    tabNameView.setTextColor(getTextColor(it))
                    indicator.visibility = GONE
                }
            }
        } else if (iTabInfo.tabType == HiTabTopInfo.TabType.BITMAP) {
            if (init) {
                tabImageView.visibility = VISIBLE
                tabNameView.visibility= GONE
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