package com.example.androidtinkoffhomework1_sokolov

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Receiver", "Receiver is working")
        context.startActivity(Intent(context, MainActivity::class.java)
            .putExtra("number", intent.getStringExtra("number"))
            .addFlags(FLAG_ACTIVITY_NEW_TASK))
    }
}