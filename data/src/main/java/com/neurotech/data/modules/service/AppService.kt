package com.neurotech.test.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
//import com.neurotech.test.App
import com.neurotech.test.bluetooth.data.GsrData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

const val FOREGROUND_ID = 1

class AppService: Service() {
    @Inject
    lateinit var bluetoothData: GsrData

    private val dataFlowAnalyzer = DataFlowAnalyzer(applicationContext)
    private val binder = LocalBinder()
    private val notificationBuilderApp by lazy { NotificationBuilderApp(applicationContext) }


    override fun onCreate() {
        super.onCreate()
        //(applicationContext as App).component.inject(this)
        CoroutineScope(Dispatchers.IO).launch{
            setBackgroundListeners()
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(FOREGROUND_ID, notificationBuilderApp.buildForegroundNotification())
        return super.onStartCommand(intent, flags, startId)
    }

    private suspend fun setBackgroundListeners() {
        setTonicListeners()
        setPhaseListeners()
        setTimeListener()
    }

    private suspend fun setPhaseListeners() {
        bluetoothData.getPhaseValueFlow().collect{
            dataFlowAnalyzer.putPhaseFlow(PhaseModelService(it.value,it.time))
        }
    }



    private suspend fun setTonicListeners() {
        bluetoothData.getTonicValueFlow().collect{
            dataFlowAnalyzer.putTonicFlow(TonicModelService(it.value,it.time))
        }
    }

    private suspend fun setTimeListener() {
        while (true){
            if(Calendar.getInstance()[Calendar.MINUTE] % 10 == 0){
                dataFlowAnalyzer.writeResultTenMinutes()
            }
            delay(30000)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): AppService = this@AppService
    }
}