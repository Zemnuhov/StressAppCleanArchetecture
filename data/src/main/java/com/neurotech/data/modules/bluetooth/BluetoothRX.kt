package com.neurotech.data.modules.bluetooth

import android.content.Context
import android.util.Log
import com.jakewharton.rx.ReplayingShare
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.modules.settings.SettingsApi
import com.neurotech.domain.BleConstant
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleDevice
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class BluetoothRX(context: Context) {


    companion object {
        val notificationDataUUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
        val writePeaksUUID: UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb")
        val writeTonicUUID: UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb")
        val writeTimeUUID: UUID = UUID.fromString("0000ffe4-0000-1000-8000-00805f9b34fb")
    }

    var client: RxBleClient = RxBleClient.create(context)
    private val settings = SettingsApi()
    private val disconnectObservable: PublishSubject<Boolean> = PublishSubject.create()
    private var connectionStateDisposable: Disposable? = null
    private var connectionDisposable: Disposable? = null
    private val connectionStateFlow = MutableStateFlow(BleConstant.DISCONNECTED)
    private val deviceFlow = MutableStateFlow(client.getBleDevice(settings.getDevice()))
    var connectionState = BleConstant.DISCONNECTED
    var connectionFlow = MutableStateFlow(
        deviceFlow.value
            .establishConnection(false)
            .takeUntil(disconnectObservable)
            .compose(ReplayingShare.instance())
    )

    private val defaultMAC = "00:00:00:00:00:00"

    init {
        RxJavaPlugins.setErrorHandler {}
    }

    suspend fun getDevice(): RxBleDevice {
        return deviceFlow.value
    }

    suspend fun getConnectionFlow(): Flow<String> {
        CoroutineScope(Dispatchers.IO).launch {
            deviceFlow.collect {
                connectionStateDisposable?.dispose()
                connectionStateDisposable =
                    it.observeConnectionStateChanges()
                        .subscribe(
                            { state ->
                                appLog(state.name)
                                when (state.name) {
                                    "DISCONNECTED" -> {
                                        connectionDisposable?.dispose()
                                        connectionStateFlow.value = BleConstant.DISCONNECTED
                                        connectionState = BleConstant.DISCONNECTED
                                        if (settings.getDevice() != defaultMAC){
                                            updateBleConnection()
                                        }
                                    }
                                    "CONNECTED" -> {
                                        connectionStateFlow.value = BleConstant.CONNECTED
                                        connectionState = BleConstant.CONNECTED
                                    }

                                    "CONNECTING" -> {
                                        connectionStateFlow.value = BleConstant.CONNECTING
                                        connectionState = BleConstant.CONNECTING
                                    }
                                    "DISCONNECTING" -> {
                                        connectionStateFlow.value = BleConstant.DISCONNECTING
                                        connectionState = BleConstant.DISCONNECTING
                                    }
                                    else -> appLog("Not enum State")
                                }
                            },
                            { exception ->
                                appLog( exception.message.toString())
                            }
                        )
            }
        }

        return connectionStateFlow
    }

    fun disconnectDevice() {
        settings.saveDevice(defaultMAC)
        disconnectObservable.onNext(true)
        connectionStateFlow.value = BleConstant.DISCONNECTED
        connectionDisposable?.dispose()
        connectionFlow.value = null
        appLog( "Disconnect device. Save default device ${settings.getDevice()}")
    }

    fun connectDevice(MAC: String) {
        settings.saveDevice(MAC)
        appLog("Connect to ${settings.getDevice()}")
        deviceFlow.value = client.getBleDevice(settings.getDevice())
        updateBleConnection()
    }

    private fun updateBleConnection() {
        connectionFlow.value = deviceFlow.value
            .establishConnection(false)
            .takeUntil(disconnectObservable)
            .compose(ReplayingShare.instance())
        connectionDisposable = connectionFlow.value
            .flatMapSingle { rxBleConnection ->
                rxBleConnection.writeCharacteristic(notificationDataUUID,"1".toByteArray())
            }
            .subscribe({connectionDisposable?.dispose()}, {})
    }

}





