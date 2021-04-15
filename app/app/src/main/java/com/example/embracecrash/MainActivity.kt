package com.example.embracecrash

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        lateinit var crashAction: String

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val snackbar = Snackbar
                    .make(view, "Message is deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        crashAction.length
                        val snackbar1 = Snackbar.make(view, crashAction.length.toString(), Snackbar.LENGTH_SHORT)
                        snackbar1.show()
                    }
            snackbar.show()
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
}