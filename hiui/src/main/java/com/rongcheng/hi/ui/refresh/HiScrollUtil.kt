package com.rongcheng.hi.ui.refresh

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

class HiScrollUtil {
    companion object {
        /**
         * 查找可以滚动的child
         * return 可滚动的View
         */
        fun findScrollableChild(viewGroup: ViewGroup): View? {
            var child = viewGroup.getChildAt(1)

            if (child is RecyclerView || child is AdapterView<*>) {
                return child
            }
            if (child is ViewGroup) {//往下多找一层
                val tempView: ViewGroup = child.getChildAt(0) as ViewGroup
                if (tempView is RecyclerView || tempView is AdapterView<*>) {
                    child = tempView
                }
            }
            return child
        }

        /**
         * 判断[child]是否发生滚动
         */
        fun childScrolled(child: View?): Boolean {
            if (child==null) return false
            if (child is AdapterView<*>) {
                if (child.firstVisiblePosition != 0
                    || child.firstVisiblePosition == 0 && child.getChildAt(0) != null
                    && child.getChildAt(0).top < 0
                )
                    return true
            } else if (child.scrollY > 0) {
                return true
            }

            if (child is RecyclerView) {
                val view = child.getChildAt(0)
                val firstPotion = child.getChildAdapterPosition(
                    view
                )
                return firstPotion != 0 || view.top != 0
            }

            return false
        }
    }


}