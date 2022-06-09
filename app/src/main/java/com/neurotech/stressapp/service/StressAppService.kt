package com.neurotech.stressapp.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class StressAppService(private val context: Context): Service {

    var isBound = MutableStateFlow(false)
    var appService: AppService? = null


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as AppService.LocalBinder
            appService = binder.getService()
            isBound.value = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound.value = false
        }

    }

    override fun bindService() {
        Intent(context, AppService::class.java).also { intent ->
            context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
        }
    }

    override fun unbindService() {
        context.unbindService(serviceConnection)
        isBound.value = false
    }

    override fun getService(): AppService? {
        return appService
    }

    override fun getBoundStateService(): Flow<Boolean> {
        return isBound
    }
}