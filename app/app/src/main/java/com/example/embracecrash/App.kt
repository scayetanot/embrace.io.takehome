package com.example.embracecrash

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log


open class App: Application(), Application.ActivityLifecycleCallbacks {

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    val LOGGING_TOOL_TAG = "MyLogToolSCA"

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this);
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        //TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            Log.v(LOGGING_TOOL_TAG, "Application is going to foreground")
        }
    }

    override fun onActivityResumed(activity: Activity) {
       // TODO("Not yet implemented")
    }

    override fun onActivityPaused(activity: Activity) {
      //  TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations;
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            Log.v(LOGGING_TOOL_TAG, "Application is going to background")
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        //TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
       // TODO("Not yet implemented")
    }
}
