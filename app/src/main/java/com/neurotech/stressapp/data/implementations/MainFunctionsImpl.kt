package com.neurotech.stressapp.data.implementations

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.ble.BleConnection
import com.neurotech.stressapp.data.ble.BleService
import com.neurotech.stressapp.domain.repository.MainFunctions
import javax.inject.Inject

class MainFunctionsImpl: MainFunctions {

    @Inject
    lateinit var bleConnection: BleConnection
    lateinit var bleService: BleService
    private var isConnectedService = false

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BleService.LocalBinder
            bleService = binder.getService()
            isConnectedService = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isConnectedService = false
        }
    }

    init {
        Singleton.daggerComponent.inject(this)
        val intent = Intent(Singleton.context, BleService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Singleton.context.applicationContext.startForegroundService(intent)
        }
        Singleton.context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun getDeviceState(): LiveData<Int> {
        TODO("Not yet implemented")
    }

    override fun disconnectDevice() {
        bleConnection.disconnectDevice()
    }
}