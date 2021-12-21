package com.rongcheng.hi.ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.rongcheng.hi.library.util.HiDisplayUtil
import com.rongcheng.hi.ui.R

class HiCircleIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), HiIndicator<FrameLayout> {
    private val vwc = ViewGroup.LayoutParams.WRAP_CONTENT


    @DrawableRes
    private var mPointNormal = R.drawable.shape_point_normal

    @DrawableRes
    private var mPointSelected = R.drawable.shape_point_select
    private var mPointLeftRightPadding: Int = 0
    private var mPointTopBottomPadding: Int = 0

    init {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(5f, context.resources).toInt()

        mPointTopBottomPadding = HiDisplayUtil.dp2px(15f, context.resources).toInt()
    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) return

        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL
        var imageView: ImageView? = null
        val imageViewParams = LinearLayout.LayoutParams(vwc, vwc)
        imageViewParams.gravity = Gravity.CENTER_VERTICAL
        imageViewParams.setMargins(
            mPointLeftRightPadding,
            mPointTopBottomPadding,
            mPointLeftRightPadding,
            mPointTopBottomPadding,
        )
        for (i in 0 until count) {
            imageView = ImageView(context)
            imageView.layoutParams = imageViewParams
            if (i == 0) {
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            groupView.addView(imageView)
        }

        val groupViewParams = LayoutParams(vwc, vwc)
        groupViewParams.gravity = Gravity.CENTER and Gravity.BOTTOM
        addView(groupView, groupViewParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup: ViewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }
}