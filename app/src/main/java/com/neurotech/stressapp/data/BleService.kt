package com.neurotech.stressapp.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.neurotech.stressapp.App
import com.neurotech.stressapp.Singleton
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

class BleService : Service() {

    @Inject
    lateinit var bleConnection: BleConnection

    val compositeDisposable = CompositeDisposable()

    companion object {
        //--------------Device UUID--------------//
        val notificationDataUUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
        val writePeaksUUID: UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb")
        val writeTonicUUID: UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb")
        val writeTimeUUID: UUID = UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb")
    }

    private val binder = LocalBinder()

    override fun onCreate() {
        (applicationContext as App).component.inject(this)
        super.onCreate()
        setBackgroundListeners()
    }

    private fun setBackgroundListeners() {
        setTonicListeners()
    }

    fun setPhaseListeners() {

    }

    private fun setTonicListeners() {
        compositeDisposable.add(bleConnection.tonicValueObservable.subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    if (Singleton.DEBUG) {
                        Log.i("ServiceDataTonic", it.toString())
                    }
                },
                {
                    Log.e("ServiceDataTonic", it.toString())
                }
            ))
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): BleService = this@BleService
    }

}