package com.rongcheng.hi.ui.app

import android.util.Log
import androidx.lifecycle.*

class ApplicationLifecycleObserver : LifecycleEventObserver {

//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    fun onCreate(){
//
//    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.e("LifecycleObserver","event : ${event.name}")
    }
}