package com.neurotech.data.modules.bluetooth.connection

import android.util.Log
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.Bluetooth
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BluetoothConnection : Connection {

    @Inject
    lateinit var bluetooth: Bluetooth

    private val deviceListFlow = MutableStateFlow<List<DeviceModelBluetooth>>(listOf())
    private val deviceList = mutableListOf<DeviceModelBluetooth>()
    private val scanStateFlow = MutableSharedFlow<Boolean>()
    private val connectionStateFlow = MutableSharedFlow<String>()

    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        component.inject(this)
    }

    override suspend fun getDeviceListFlow(): Flow<List<DeviceModelBluetooth>> {
//        deviceListFlow.value = listOf()
//        scope.launch {
//            scanStateFlow.emit(true)
//            launch {
//                bluetooth.central.scanForPeripherals(
//                    { peripheral, _ ->
//                        val device = DeviceModelBluetooth(peripheral.name, peripheral.address)
//                        if (device !in deviceList && device.name.isNotEmpty()){
//                            deviceList.add(device)
//                        }
//                        deviceListFlow.value = deviceList
//                    },
//                    { scanFailure ->
//                        Log.e("DeviceScan", scanFailure.toString())
//                    }
//                )
//            }
//            launch {
//                delay(10000L)
//                stopScan()
//            }
//        }
        return deviceListFlow
    }

    override suspend fun getScanState(): Flow<Boolean> {
        scanStateFlow.emit(false)
        return scanStateFlow
    }

    override suspend fun stopScan() {
//        bluetooth.central.stopScan()
//        scanStateFlow.emit(false)
    }


    override suspend fun connectionToPeripheral(MAC: String) {
//        bluetooth._peripheral = bluetooth.central.getPeripheral(MAC)
//        bluetooth.peripheral.autoConnect()
    }

    override suspend fun getConnectionStateFlow(): SharedFlow<String> {
//        bluetooth.central.observeConnectionState { _, state ->
//            CoroutineScope(Dispatchers.IO).launch {
//                connectionStateFlow.emit(state.name)
//            }
//        }
        return connectionStateFlow
    }

    override suspend fun getConnectionState(): String {
        return "" //bluetooth.peripheral.getState().name
    }

    override suspend fun disconnectDevice() {
//        bluetooth.central.cancelConnection(bluetooth.peripheral)
    }
}