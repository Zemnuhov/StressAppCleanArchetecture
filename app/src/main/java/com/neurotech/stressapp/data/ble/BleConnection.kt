package com.neurotech.stressapp.data.ble

import android.util.Log
import com.jakewharton.rx.ReplayingShare
import com.neurotech.stressapp.Singleton
import com.neurotech.stressapp.data.filters.ExpRunningAverage
import com.neurotech.stressapp.data.filters.KalmanFilter
import com.neurotech.stressapp.data.filters.ValueConverter
import com.neurotech.stressapp.data.spsettings.SettingsApi
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.HashMap


class BleConnection {
    var client: RxBleClient = RxBleClient.create(Singleton.context)
    private var device: RxBleDevice? = null
    private var connection: Observable<RxBleConnection>? = null
    var connectionState = DISCONNECTED
    val connectionStateObservable: PublishSubject<String> = PublishSubject.create()
    val tonicValueObservable: PublishSubject<HashMap<String, Any>> = PublishSubject.create()
    val phaseValueObservable: PublishSubject<HashMap<String, Any>> = PublishSubject.create()
    private val disconnectObservable: PublishSubject<Boolean> = PublishSubject.create()
    private var compositeDisposable = CompositeDisposable()
    private val valueConverter = ValueConverter()

    private val notificationDataUUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    private val writePeaksUUID: UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb")
    private val writeTonicUUID: UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb")
    private val writeTimeUUID: UUID = UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb")


    companion object {
        const val DISCONNECTED = "DISCONNECTED"
        const val DISCONNECTING = "DISCONNECTING"
        const val CONNECTED = "CONNECTED"
        const val CONNECTING = "CONNECTING"
    }

    init {
        updateBleDevice(SettingsApi().getDevice()!!)
        connectionStateObserver()
    }

    private fun connectionStateObserver() {
        compositeDisposable.add(device!!
            .observeConnectionStateChanges()
            .subscribe(
                {
                    Log.i("DeviceState", "State:${it.name} Name:${device?.name}")
                    when (it.name) {
                        "DISCONNECTED" -> {
                            connectionStateObservable.onNext(DISCONNECTED)
                            connectionState = DISCONNECTED
                            val settingsApi = SettingsApi()
                            if (settingsApi.getDevice() != settingsApi.defaultMAC) {
                                updateBleConnection()
                            }
                        }
                        "CONNECTED" -> {
                            connectionStateObservable.onNext(CONNECTED)
                            connectionState = CONNECTED
                        }

                        "CONNECTING" -> {
                            connectionStateObservable.onNext(CONNECTING)
                        }
                        "DISCONNECTING" -> {
                            connectionStateObservable.onNext(DISCONNECTING)
                        }
                        else -> Log.e("DeviceState", "Not enum State")
                    }
                },
                {
                    Log.e("BleConnectionObserver", it.message.toString())
                }
            )
        )

    }

    fun updateBleDevice(MAC: String) {
        disconnectDevice()
        device = client.getBleDevice(MAC)
        connectionStateObserver()
        updateBleConnection()
    }

    private fun updateBleConnection() {
        connection = device!!
            .establishConnection(true)
            .takeUntil(disconnectObservable)
            .compose(ReplayingShare.instance())

        tonicValue()
        phaseValue()

    }

    fun disconnectDevice() {
        disconnectObservable.onNext(true)
        connectionStateObservable.onNext(DISCONNECTED)
        compositeDisposable.clear()
        connection = null
        device = null
    }

    private fun tonicValue() {
        val kalmanFilter = KalmanFilter(0.0, 0.1)
        val expRunningAverage = ExpRunningAverage(0.01)
        compositeDisposable.add(connection!!
            .subscribeOn(Schedulers.computation())
            .flatMap { rxBleConnection -> rxBleConnection.setupNotification(notificationDataUUID) }
            .flatMap { it }
            .map { ByteBuffer.wrap(it).int }
            .map { valueConverter.rangeConvert(it) }
            .map { kalmanFilter.correct(it) }
            .map { expRunningAverage.filter(it).toInt() }
            .subscribe(
                { tonicValueObservable.onNext(hashMapOf("value" to it, "time" to Date())) },
                { Log.e("BleConTonic", it.toString()) }
            )
        )

    }

    private fun phaseValue() {
        val kalmanFilter = KalmanFilter(0.0, 0.1)
        val expRunningAverage = ExpRunningAverage(0.01)
        val expRunningAverageTonic = ExpRunningAverage(0.1)
        compositeDisposable.add(connection!!
            .flatMap { rxBleConnection -> rxBleConnection.setupNotification(notificationDataUUID) }
            .flatMap { it }
            .map { ByteBuffer.wrap(it).int }
            .map { valueConverter.rangeConvert(it) }
            .map { expRunningAverageTonic.filter(it).toInt() }
            .map { valueConverter.toPhaseValue(it) }
            .map { kalmanFilter.correct(it) }
            .map { expRunningAverage.filter(it) }
            .subscribe(
                { phaseValueObservable.onNext(hashMapOf("value" to it, "time" to Date())) },
                { Log.e("BleConPhase", it.toString()) }
            )
        )
    }
}