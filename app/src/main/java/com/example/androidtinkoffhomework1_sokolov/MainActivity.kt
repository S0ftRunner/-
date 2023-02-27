package com.example.androidtinkoffhomework1_sokolov

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        buttonClick.setOnClickListener {
            val name = editTextName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, getString(R.string.alert), Toast.LENGTH_SHORT).show()
            } else {
                startNewWindow(name)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if (intent.getStringExtra("number") != null) {
            textViewRandomNumber.visibility = View.VISIBLE
            Log.d(TAG, "it is work, number is ${intent.getStringExtra("number")}")
            val showRandom : String = getString(
                R.string.random_answer_for_user,
                intent.getStringExtra("number")
            )
            textViewRandomNumber.text = showRandom
        }
    }

    fun startNewWindow(name: String) {
        val intent = Downloader.newIntent(this, name)
        startActivity(intent)
    }

}