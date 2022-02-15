package com.rongcheng.hi.ui.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.rongcheng.hi.ui.app.banner.frc.FBannerDemoActivity
import com.rongcheng.hi.ui.app.databinding.ActivityMainBinding
import com.rongcheng.hi.ui.app.refresh.HiRefreshLayoutDemoActivity
import com.rongcheng.hi.ui.app.tab.HiTabBottomDemoActivity
import com.rongcheng.hi.ui.app.tab.HiTabTopDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.refresh.setOnClickListener(this)
        binding.tabBottomLayout.setOnClickListener(this)
        binding.tabTopLayout.setOnClickListener(this)
        binding.banner.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()
        binding.banner.post {
            Log.e("onResume", "w: " + binding.banner.width)
        }
        binding.banner.viewTreeObserver.addOnGlobalLayoutListener {
            binding.banner.viewTreeObserver.removeOnGlobalLayoutListener { this }
            Log.e("viewTreeObserver", "w: " + binding.banner.width)
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tabBottomLayout -> startActivity(
                Intent(
                    this,
                    HiTabBottomDemoActivity::class.java
                )
            )
            binding.tabTopLayout -> startActivity(Intent(this, HiTabTopDemoActivity::class.java))
            binding.refresh -> startActivity(Intent(this, HiRefreshLayoutDemoActivity::class.java))
            binding.banner -> startActivity(Intent(this, FBannerDemoActivity::class.java))
        }
    }
}