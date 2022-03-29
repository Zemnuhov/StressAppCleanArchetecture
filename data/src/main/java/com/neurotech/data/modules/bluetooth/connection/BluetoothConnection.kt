package com.neurotech.data.modules.bluetooth.connection

import android.content.Context
import android.util.Log
//import com.neurotech.stressapp.App
import com.neurotech.test.bluetooth.Bluetooth
import com.neurotech.test.bluetooth.connection.Connection
import com.neurotech.test.bluetooth.connection.DeviceModelBluetooth
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class BluetoothConnection(context: Context) : Connection {

    @Inject
    lateinit var bluetooth: Bluetooth

    private val deviceListFlow = MutableSharedFlow<List<DeviceModelBluetooth>>()
    private val deviceSet = mutableSetOf<DeviceModelBluetooth>()
    private val scanStateFlow = MutableSharedFlow<Boolean>()
    private val connectionStateFlow = MutableSharedFlow<String>()

    init {
        //(context as App).component.inject(this)
    }

    override suspend fun getDeviceListFlow(): Flow<List<DeviceModelBluetooth>> {
        coroutineScope {
            scanStateFlow.emit(true)
            bluetooth.central.scanForPeripherals(
                { peripheral, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        deviceSet.add(DeviceModelBluetooth(peripheral.name, peripheral.address))
                        deviceListFlow.emit(deviceSet.toList())
                    }

                },
                { scanFailure ->
                    Log.e("DeviceScan", scanFailure.toString())
                }
            )
            CoroutineScope(Dispatchers.Default).launch {
                delay(10000L)
                stopScan()
            }
        }
        return deviceListFlow
    }

    override suspend fun getScanState(): Flow<Boolean> {
        scanStateFlow.emit(false)
        return scanStateFlow
    }

    override suspend fun stopScan() {
        bluetooth.central.stopScan()
        scanStateFlow.emit(false)
    }


    override suspend fun connectionToPeripheral(MAC: String) {
        bluetooth._peripheral = bluetooth.central.getPeripheral(MAC)
        bluetooth.peripheral.autoConnect()
    }

    override suspend fun getConnectionStateFlow(): Flow<String> {
        bluetooth.central.observeConnectionState { _, state ->
             CoroutineScope(Dispatchers.IO).launch {
                 connectionStateFlow.emit(state.name)
             }
         }
        return connectionStateFlow
    }

    override suspend fun disconnectDevice() {
        bluetooth.central.cancelConnection(bluetooth.peripheral)
    }
}