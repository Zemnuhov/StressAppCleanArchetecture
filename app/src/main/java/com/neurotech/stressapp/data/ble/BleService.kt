package com.neurotech.stressapp.data.ble

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.neurotech.stressapp.App
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.DataFlowAnalyzer
import com.neurotech.stressapp.data.database.AppDatabase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BleService : Service() {

    @Inject
    lateinit var bleConnection: BleConnection
    @Inject
    lateinit var dataBase: AppDatabase

    private val compositeDisposable = CompositeDisposable()
    private val dataFlowAnalyzer = DataFlowAnalyzer()
    private val binder = LocalBinder()


    override fun onCreate() {
        (applicationContext as App).component.inject(this)
        super.onCreate()
        setBackgroundListeners()
    }

    private fun setBackgroundListeners() {
        setTonicListeners()
        setPhaseListeners()
        setTimeListener()
    }

    private fun setPhaseListeners() {
        compositeDisposable.add(bleConnection.phaseValueObservable
            .subscribeOn(Schedulers.computation())
            .subscribe {
                dataFlowAnalyzer.putPhaseFlow(it)
            }
        )
    }

    private fun setTonicListeners() {
        compositeDisposable.add(bleConnection.tonicValueObservable.subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    if (Singleton.DEBUG) {
                        Log.i("ServiceDataTonic", it.toString())
                    }
                    dataFlowAnalyzer.putTonicFlow(it)
                },
                {
                    Log.e("ServiceDataTonic", it.toString())
                }
            )
        )
    }

    private fun setTimeListener() {
        compositeDisposable.add(Observable.interval(30, TimeUnit.SECONDS)
            .subscribe {
                if(Calendar.getInstance()[Calendar.MINUTE] % 10 == 0){
                    dataFlowAnalyzer.writeResultTenMinutes()
                }
            }
        )
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): BleService = this@BleService
    }

}