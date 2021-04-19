package com.example.embracecrash

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.embracecrash.App.Companion.IS_CRASH
import com.example.embracecrash.App.Companion.TS
import com.example.embracecrash.data.LogEntity
import com.example.embracecrash.databinding.ActivityMainBinding
import com.example.embracecrash.utils.CustomExceptionHandler
import com.example.embracecrash.utils.viewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var fabMain: FloatingActionButton
    private lateinit var fabCrash:FloatingActionButton
    private lateinit var fabDelete:FloatingActionButton
    private lateinit var fabOpen: Animation
    private lateinit var fabClose:Animation
    private lateinit var fabClock:Animation
    private lateinit var fabAnticlock:Animation
    private lateinit var textviewClearLog: TextView
    private lateinit var textviewCrash:TextView

    var isOpen = false
    lateinit var iAmInChargeOfTheCrash: String

    private lateinit var logViewAdapter: LogViewAdapter
    private val appComponents by lazy { App.appComponents }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences


    fun getViewModel(): MainActivityViewModel {
        return viewModelProvider(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponents.inject(this)
        super.onCreate(savedInstanceState)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (Thread.getDefaultUncaughtExceptionHandler() !is CustomExceptionHandler) {
            Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler(this))
        }

        if(isCrashLastTime()){
            val timestamp = preferences.getLong(TS, 0L).toString()
            Toast.makeText(this, "crash happened at $timestamp", Toast.LENGTH_LONG).show();
            preferences.edit().putBoolean(IS_CRASH, false).apply()
        }
        initViews()
        initObservers()
        initFloatingActionButton()
    }

    private fun initViews() {
        getViewModel().getLogs()
    }

    fun isCrashLastTime(): Boolean {
        return preferences.getBoolean(IS_CRASH, false)
    }

    private fun initObservers() {
        getViewModel().resultLogs.observe(this, Observer { logs ->
            logs?.let {
                initRecycler(it)
            }
        })

        getViewModel().errorMessage.observe(this, Observer {
            Toast.makeText(this,"Connection Error", Toast.LENGTH_LONG).show();
        })
    }

    private fun initRecycler(list: List<LogEntity>) {
        if (!list.isNullOrEmpty()) {
            logViewAdapter = LogViewAdapter(list)
            binding.recycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = logViewAdapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initFloatingActionButton(){
        fabMain = findViewById(R.id.fab);
        fabCrash = findViewById(R.id.fab_crash);
        fabDelete = findViewById(R.id.fab_delete);
        fabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close);
        fabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open);
        fabClock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_clock);
        fabAnticlock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_anticlock);

        textviewClearLog = findViewById(R.id.textview_clear_log);
        textviewCrash = findViewById(R.id.textview_crash);


        fabMain.setOnClickListener {
            if (isOpen) {
                textviewClearLog.visibility = View.INVISIBLE
                textviewCrash.visibility = View.INVISIBLE
                fabCrash.startAnimation(fabClose)
                fabDelete.startAnimation(fabClose)
                fabMain.startAnimation(fabAnticlock)
                fabCrash.isClickable = false
                fabDelete.isClickable = false
                isOpen = false
            } else {
                textviewClearLog.visibility = View.VISIBLE
                textviewCrash.visibility = View.VISIBLE
                fabCrash.startAnimation(fabOpen)
                fabDelete.startAnimation(fabOpen)
                fabMain.startAnimation(fabClock)
                fabCrash.isClickable = true
                fabDelete.isClickable = true
                isOpen = true
            }
        }

        fabCrash.setOnClickListener {
            iAmInChargeOfTheCrash.length
            Toast.makeText(applicationContext, iAmInChargeOfTheCrash.length.toString(), Toast.LENGTH_SHORT).show()
        }

        fabDelete.setOnClickListener {
            getViewModel().deleteAll()
        }
    }
}