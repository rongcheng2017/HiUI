package com.rongcheng.hi.ui.tab.bottom

import android.graphics.Bitmap
import androidx.fragment.app.Fragment

class HiTabBottomInfo<Color> {

    enum class TabType {
        BITMAP, ICON
    }


    lateinit var iconFont: String
        private set
    var defaultColor: Color? = null
        private set
    var tintColor: Color? = null
        private set
    var selectedIconName: String? = null
        private set
    var defaultIconName: String? = null
        private set

    var name: String
        private set
    var selectedBitmap: Bitmap? = null
        private set
    var defaultBitmap: Bitmap? = null
        private set

    var fragment: Class<out Fragment?>? = null
    val tabType: TabType

    constructor(defaultBitmap: Bitmap?, selectedBitmap: Bitmap?, name: String) {
        this.defaultBitmap = defaultBitmap
        this.selectedBitmap = selectedBitmap
        this.name = name
        this.tabType = TabType.BITMAP
    }

    constructor(
        defaultIconName: String?,
        selectedIconName: String?,
        defaultColor: Color,
        tintColor: Color,
        name: String,
        iconFont: String
    ) {
        this.name = name
        this.iconFont = iconFont
        this.defaultIconName = defaultIconName
        this.selectedIconName = selectedIconName
        this.defaultColor = defaultColor
        this.tintColor = tintColor
        this.tabType = TabType.ICON
    }


}