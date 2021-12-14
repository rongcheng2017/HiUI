package com.rongcheng.hi.ui.tab.top

import android.graphics.Bitmap
import androidx.fragment.app.Fragment

class HiTabTopInfo<Color> {

    enum class TabType {
        BITMAP, TEXT
    }


    var defaultColor: Color? = null
        private set
    var tintColor: Color? = null
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
        name: String,
        defaultColor: Color,
        tintColor: Color,

        ) {
        this.name = name
        this.defaultColor = defaultColor
        this.tintColor = tintColor
        this.tabType = TabType.TEXT
    }


}