package com.neurotech.stressapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.cesarferreira.tempo.toString
import com.neurotech.domain.TimeFormat
import com.neurotech.domain.usecases.gsrdata.GetPhaseValueFlow
import com.neurotech.domain.usecases.gsrdata.GetTonicValueFlow
import com.neurotech.stressapp.App
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.notification.NotificationBuilderApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.zip
import java.lang.Exception
import java.util.*
import javax.inject.Inject

const val FOREGROUND_ID = 1

class AppService : Service() {
    @Inject
    lateinit var getPhaseValueFlow: GetPhaseValueFlow

    @Inject
    lateinit var getTonicValueFlow: GetTonicValueFlow

    private val dataFlowAnalyzer by lazy { DataFlowAnalyzer(applicationContext) }
    private val binder = LocalBinder()
    private val notificationBuilderApp by lazy { NotificationBuilderApp(applicationContext) }


    override fun onCreate() {
        super.onCreate()
        (application as App).component.inject(this)
        CoroutineScope(Dispatchers.IO).launch {
            setBackgroundListeners()
        }
        startForeground(FOREGROUND_ID, notificationBuilderApp.buildForegroundNotification())

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(FOREGROUND_ID, notificationBuilderApp.buildForegroundNotification())
        return super.onStartCommand(intent, flags, startId)
    }

    private suspend fun setBackgroundListeners() = coroutineScope {
        launch { setTonicListeners() }
        launch { setPhaseListeners() }
        launch { setTimeListener() }
        ////??////////
        launch { setRecoding() }
    }

    private suspend fun setRecoding(){
        try {
            getTonicValueFlow.invoke().zip(getPhaseValueFlow.invoke()){ a,b ->
                hashMapOf("Time" to a.time,"Tonic" to a.value, "Phase" to b.value)
            }.collect{
                if(Singleton.recoding){

                    Singleton.file.appendText(
                        "${(it.get("Time") as Date).toString(TimeFormat.dateTimeFormatDataBase)}, ${it.get("Tonic")}, ${it.get("Phase")}\n"
                    )
                }
            }
        }catch (e: Exception){
            Log.e("AAA", e.toString())
        }

    }

    private suspend fun setPhaseListeners() {
        getPhaseValueFlow.invoke().collect {
            dataFlowAnalyzer.putPhaseFlow(PhaseModelService(it.value, it.time))
        }
    }


    private suspend fun setTonicListeners() {
        getTonicValueFlow.invoke().collect {
            dataFlowAnalyzer.putTonicFlow(TonicModelService(it.value, it.time))
        }
    }

    private suspend fun setTimeListener() {
        while (true) {
            if (Calendar.getInstance()[Calendar.MINUTE] % 10 == 0) {
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