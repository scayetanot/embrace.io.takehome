package com.example.embracecrash

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.example.embracecrash.data.LogEntity
import com.example.embracecrash.di.component.AppComponents
import com.example.embracecrash.di.component.DaggerAppComponents
import com.example.embracecrash.di.modules.AppModule
import com.example.embracecrash.di.modules.StorageModule
import com.example.embracecrash.utils.CustomExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


open class App: Application(), Application.ActivityLifecycleCallbacks {

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false
    val LOGGING_TOOL_TAG = "MyLogToolSCA"
    private lateinit var preferences: SharedPreferences


    companion object {
        lateinit var appComponents: AppComponents
        val CRASH = "crash"
        val DFLT: String = "default"
        val COLD: String = "cold"
        private const val FIRST_LAUNCH = "first_launch"
        const val IS_CRASH = "crash"
        const val TS = "ts"
    }

    fun isColdStart(): Boolean {
        return preferences.getBoolean(FIRST_LAUNCH, true)
    }

    fun unFlagColdStart() {
        preferences.edit().putBoolean(FIRST_LAUNCH, false).apply()
    }

    override fun onCreate() {
        super.onCreate()
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        appComponents = initDagger(this)
        registerActivityLifecycleCallbacks(this);
    }

    private fun initDagger(app: App): AppComponents =
        DaggerAppComponents.builder()
            .appModule(AppModule(app))
            .storageModule(StorageModule(app))
            .build()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            submitLog(LogEntity(
                    System.currentTimeMillis(),
                    if(isColdStart()) COLD else DFLT,
                    "Application is going to foreground"
            ))
            Log.v(LOGGING_TOOL_TAG, "Application is going to foreground")
        }
    }

    private fun submitLog(entry: LogEntity){
        MainScope().launch {
            try {
                appComponents.appDataObject().addEntry(entry)
            } catch (e: Exception) {
                Log.e(App.javaClass.name, e.message.toString())
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if(isColdStart()) unFlagColdStart()
        // TODO("Not yet implemented")
    }

    override fun onActivityPaused(activity: Activity) {
      //  TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations;
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            submitLog(LogEntity(
                    System.currentTimeMillis(),
                    DFLT,
                    "Application is going to background"
            ))
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
