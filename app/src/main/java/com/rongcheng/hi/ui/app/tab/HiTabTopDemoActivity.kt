package com.rongcheng.hi.ui.app.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rongcheng.hi.ui.app.R
import com.rongcheng.hi.ui.tab.common.OnTabSelectedListener
import com.rongcheng.hi.ui.tab.top.HiTabTopInfo
import com.rongcheng.hi.ui.tab.top.HiTapTopLayout

class HiTabTopDemoActivity : AppCompatActivity() {
    val tabStr = arrayListOf("热门", "服装", "数码","热门", "服装", "数码","热门", "服装", "数码","热门", "服装", "数码",)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_top_demo)
        initTabTop()

    }

    private fun initTabTop() {

        val hiTabTopLayout: HiTapTopLayout = findViewById(R.id.tab_top_layout)
        val infoList: MutableList<HiTabTopInfo<*>> =ArrayList()
        val defaultColor = resources.getColor(R.color.tabTopDefaultColor)
        val tintColor = resources.getColor(R.color.tabTopTintColor)
        tabStr.forEach {
            val info = HiTabTopInfo(it, defaultColor, tintColor)
            infoList.add(info)
        }
        hiTabTopLayout.inflateInfo(infoList)
        hiTabTopLayout.addTabSelectedChangeListener( object :OnTabSelectedListener<HiTabTopInfo<*>>{
            override fun onTabSelectedChange(
                index: Int,
                prevInfo: HiTabTopInfo<*>?,
                nextInfo: HiTabTopInfo<*>?
            ) {
                Toast.makeText(this@HiTabTopDemoActivity, nextInfo?.name, Toast.LENGTH_SHORT)
                    .show()
            }
        })
        hiTabTopLayout.defaultSelected(infoList[0])
    }


}