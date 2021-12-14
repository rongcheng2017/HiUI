package com.rongcheng.hi.ui.app.tab

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rongcheng.hi.library.util.HiDisplayUtil
import com.rongcheng.hi.ui.app.R
import com.rongcheng.hi.ui.tab.bottom.HiTabBottomInfo
import com.rongcheng.hi.ui.tab.bottom.HiTabBottomLayout
import com.rongcheng.hi.ui.tab.common.OnTabSelectedListener

class HiTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)
        initTabBottom()

    }

    private fun initTabBottom() {

        val hiTabBottomLayout = findViewById<HiTabBottomLayout>(R.id.hi_tab_layout)
        hiTabBottomLayout.bottomAlpha = 0.85f
        val bottomInfoList: MutableList<HiTabBottomInfo<*>> = ArrayList()
        val homeInfo =
            HiTabBottomInfo(
                name = "首页",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = getString(R.string.if_home),
                selectedIconName = null,
                defaultColor = "#ff656667",
                tintColor = "#ffd44949"
            )

        val infoRecommend =
            HiTabBottomInfo(
                name = "收藏",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = getString(R.string.if_favorite),
                selectedIconName = null,
                defaultColor = "#ff656667",
                tintColor = "#ffd44949"
            )
//        val infoCategory =
//            HiTabBottomInfo(
//                name = "分类",
//                iconFont = "fonts/iconfont.ttf",
//                defaultIconName = getString(R.string.if_category),
//                selectedIconName = null,
//                defaultColor = "#ff656667",
//                tintColor = "#ffd44949"
//            )
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.fire, null)
        val infoCategory = HiTabBottomInfo<String>(
            name = "分类",
            defaultBitmap = bitmap,
            selectedBitmap = bitmap
        )
        val infoChat =
            HiTabBottomInfo(
                name = "推荐",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = getString(R.string.if_recommend),
                selectedIconName = null,
                defaultColor = "#ff656667",
                tintColor = "#ffd44949"
            )
        val infoProfile =
            HiTabBottomInfo(
                name = "我的",
                iconFont = "fonts/iconfont.ttf",
                defaultIconName = getString(R.string.if_profile),
                selectedIconName = null,
                defaultColor = "#ff656667",
                tintColor = "#ffd44949"
            )

        bottomInfoList.add(homeInfo)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoCategory)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoProfile)
        hiTabBottomLayout.inflateInfo(bottomInfoList)
        hiTabBottomLayout.addTabSelectedChangeListener(object :
            OnTabSelectedListener<HiTabBottomInfo<*>> {
            override fun onTabSelectedChange(
                index: Int,
                prevInfo: HiTabBottomInfo<*>?,
                nextInfo: HiTabBottomInfo<*>?
            ) {
                Toast.makeText(this@HiTabBottomDemoActivity, nextInfo?.name, Toast.LENGTH_SHORT)
                    .show()
            }
        })
        hiTabBottomLayout.defaultSelected(homeInfo)
        val tabBottom = hiTabBottomLayout.findTab(bottomInfoList[2])
        tabBottom?.resetHeight(HiDisplayUtil.dp2px(66f,this.resources).toInt())
    }
}