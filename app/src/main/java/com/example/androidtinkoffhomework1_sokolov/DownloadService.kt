package com.example.androidtinkoffhomework1_sokolov

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class DownloadService : Service() {

    private val TAG = "DownloadService"

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DownloadService", "Download is going")
        sendBroadCast(this)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d("DownloadService", "Download is stop and composits deleted")
        mHandler.removeCallbacks(mRunnable)
    }

    private fun getRandomNumber() : Int {
        val rand = Random()
        val number = rand.nextInt(100)
        Log.d(TAG, "Number is $number")
        mHandler.postDelayed(mRunnable, 1000)
        return number
    }


    private fun sendBroadCast(context : Context) {
        mHandler = Handler()
        mRunnable = Runnable { getRandomNumber() }
        mHandler.postDelayed(mRunnable, 1000)
        val randomNumber = getRandomNumber().toString()
        val intent = Intent("MyAction").putExtra("number", randomNumber)
        val localBroadcastManager = LocalBroadcastManager.getInstance(context)
        localBroadcastManager.sendBroadcast(intent)
        Log.d(TAG, "send $randomNumber to intent")
        stopSelf()
    }
}