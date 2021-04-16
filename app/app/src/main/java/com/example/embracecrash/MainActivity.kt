package com.example.embracecrash

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        lateinit var iAmInChargeOfTheCrash: String

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
                textviewCrash.setVisibility(View.VISIBLE)
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

        fabDelete.setOnClickListener { Toast.makeText(applicationContext, "Delete", Toast.LENGTH_SHORT).show() }


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
}