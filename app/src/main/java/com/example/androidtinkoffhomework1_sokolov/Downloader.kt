package com.example.androidtinkoffhomework1_sokolov

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_downloader.*

class Downloader : AppCompatActivity() {

    private val myReceiver : MyReceiver = MyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloader)



    }

    override fun onStart() {
        super.onStart()
        sayHelloToUser()

        val serviceClass = DownloadService::class.java

        val intent = Intent(this, serviceClass)

        val intentFilter = IntentFilter("MyAction")
        val localBroadcastReceiver = LocalBroadcastManager.getInstance(this)
        localBroadcastReceiver.registerReceiver(myReceiver, intentFilter)

        buttonDownloadContacts.setOnClickListener{
            if (!isServiceRunning(serviceClass)) {
                startService(intent)
            } else {
                Toast.makeText(this, "Service is already running", Toast.LENGTH_SHORT).show()
            }
        }

        buttonStopService.setOnClickListener{
            if (isServiceRunning(serviceClass)) {
                stopService(intent)
            } else {
                Toast.makeText(this, "Service is already stopped", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun sayHelloToUser() {
        val greeting : String = getString(
            R.string.ask_for_user_click_button,
            intent.getStringExtra(EXTRA_USER_NAME)
        )
        textViewGreetingUser.text = greeting
    }

    companion object{
        private const val EXTRA_USER_NAME = "UserName"
        fun newIntent(context : Context, name : String) : Intent {
            val intent = Intent(context, Downloader::class.java)
            intent.putExtra(EXTRA_USER_NAME, name)
            return intent
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }


    override fun onStop() {
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.unregisterReceiver(myReceiver)
        super.onStop()
    }
}