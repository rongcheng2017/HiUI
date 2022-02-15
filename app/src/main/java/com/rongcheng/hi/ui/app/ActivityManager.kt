package com.rongcheng.hi.ui.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import java.lang.ref.WeakReference

class ActivityManager private constructor() : Application.ActivityLifecycleCallbacks {

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontbackCallback = ArrayList<(Boolean) -> Unit>()
    private var activityStartCount = 0
    private var front = true

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    fun addFrontBackCallback(callback: (Boolean) -> Unit) {
        frontbackCallback.add(callback)
    }

    fun removeFrontBackCallback(callback: (Boolean) -> Unit) {
        frontbackCallback.remove(callback)
    }

    val topActivity: Activity?
        get() =
            if (activityRefs.size <= 0) null
            else
                activityRefs[activityRefs.size - 1].get()


    companion object {
        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    private fun onFrontBackChanged(front: Boolean) {
        for (frontBackCallback in frontbackCallback) {
            frontBackCallback.invoke(front)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityRefs.add(WeakReference(activity))
    }

    override fun onActivityStarted(activity: Activity) {
        activityStartCount++
        if (!front && activityStartCount > 0) {
            front = true
        }
        onFrontBackChanged(front)
    }


    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        activityStartCount--
        if (activityStartCount <= 0 && front) {
            front = false
        }
        onFrontBackChanged(front)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        for (activityRef in activityRefs) {
            if (activityRef != null && activityRef.get() == activity) {
                activityRefs.remove(activityRef)
                break
            }
        }
    }

}