package com.rongcheng.hi.ui.app

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.rongcheng.hi.ui.app.databinding.ActivityMainBinding
import com.rongcheng.hi.ui.app.tab.HiTabBottomDemoActivity
import com.rongcheng.hi.ui.app.tab.HiTabTopDemoActivity
import com.rongcheng.hi.ui.tab.bottom.HiTabBottom

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.refresh.setOnClickListener(this)
        binding.tabBottomLayout.setOnClickListener(this)
        binding.tabTopLayout.setOnClickListener(this)
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
        }
    }
}