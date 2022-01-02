package com.neurotech.stressapp.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.neurotech.stressapp.App
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject

class BleService : Service() {

    companion object{
        //--------------Device UUID--------------//
        val notificationDataUUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
        val writePeaksUUID: UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb")
        val writeTonicUUID: UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb")
        val writeTimeUUID: UUID = UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb")
    }

    private val binder = LocalBinder()

    @Inject
    lateinit var device: RxBleDevice
    @Inject
    lateinit var connection: Observable<RxBleConnection>

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).component.inject(this)
        setBackgroundListeners()
    }

    private fun setBackgroundListeners(){
        setTonicListeners()
    }

    fun setPhaseListeners(){

    }

    private fun setTonicListeners(){
        val disposable =  connection.subscribeOn(Schedulers.computation())
            .flatMap { rxBleConnection -> rxBleConnection.setupNotification(BleService.notificationDataUUID) }
            .flatMap { it }
            .map { ByteBuffer.wrap(it).int }
            .subscribe {
                Log.e("BleService", it.toString())
            }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder: Binder(){
        fun getService():BleService = this@BleService
    }

}