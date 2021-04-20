package com.example.embracecrash.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.example.embracecrash.App
import com.example.embracecrash.App.Companion.CRASH
import com.example.embracecrash.data.LogEntity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception
import android.content.Context
import com.example.embracecrash.App.Companion.IS_CRASH
import com.example.embracecrash.App.Companion.TS
import com.google.android.material.tabs.TabLayout

class CustomExceptionHandler(private val context: Context) : Thread.UncaughtExceptionHandler {

    private var defaultUEH: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()


    override fun uncaughtException(t: Thread?, e: Throwable) {

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        preferences.edit().putBoolean(IS_CRASH, true).apply()
        preferences.edit().putLong(TS, System.currentTimeMillis()).apply()

        Log.d("SCASCAC", "-----------------------------------")
        val entry = LogEntity(
                System.currentTimeMillis(),
                CRASH,
                e.message.toString())
        MainScope().launch {
            try {
                Log.d("SCASCAC", e.message.toString())

                Log.d("SCASCAC", "-----------------------------------")
                App.appComponents.appDataObject().addEntry(entry)
            } catch (e: Exception) {
                Log.e(App.javaClass.name, e.message.toString())
            }
        }
        defaultUEH.uncaughtException(t, e)
    }

}