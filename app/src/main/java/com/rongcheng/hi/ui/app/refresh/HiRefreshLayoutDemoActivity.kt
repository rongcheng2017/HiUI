package com.rongcheng.hi.ui.app.refresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rongcheng.hi.ui.app.R
import com.rongcheng.hi.ui.refresh.HiRefreshLayout
import com.rongcheng.hi.ui.refresh.IHiRefresh
import com.rongcheng.hi.ui.refresh.TextOverView

class HiRefreshLayoutDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_refresh_layout_demo)
        val refreshLayout = findViewById<HiRefreshLayout>(R.id.refresh_layout)
        val overView = TextOverView(this)
        refreshLayout.setRefreshOverView(overView)
        refreshLayout.setRefreshListener(object : IHiRefresh.HiRefreshListener {
            override fun onRefresh() {
                Handler().postDelayed({ refreshLayout.refreshFinished() }, 1000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}