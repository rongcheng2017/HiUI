package com.rongcheng.hi.ui.app.banner.frc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.rongcheng.frc.banner.Banner
import com.rongcheng.frc.banner.BannerAdapter
import com.rongcheng.frc.banner.BannerMo
import com.rongcheng.frc.banner.IBannerBindAdapter
import com.rongcheng.hi.ui.app.R

class FBannerDemoActivity : AppCompatActivity() {
    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fbanner_demo)
        val banner = findViewById<Banner>(R.id.banner)
        val data: MutableList<BannerMo> = arrayListOf()
        for (url in urls) {
            val mo = FBannerMo()
            mo.url = url
            data.add(mo)
        }
        banner.setData(data)

        banner.setBindAdapter(object:IBannerBindAdapter{
            override fun onBind(viewHolder: BannerAdapter.ViewHolder, position: Int, mo: BannerMo) {
            }
        })

    }
}