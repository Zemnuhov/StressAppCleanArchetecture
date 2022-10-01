package com.neurotech.data.modules.bluetooth.connection

import android.util.Log
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.Bluetooth
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BluetoothConnection : Connection {

    @Inject
    lateinit var bluetooth: Bluetooth

    private val deviceListFlow = MutableStateFlow(listOf<DeviceModelBluetooth>())
    private var deviceSet = hashSetOf<DeviceModelBluetooth>()
    private lateinit var scanDisposable: Disposable
    private val scanStateFlow = MutableStateFlow(false)

    init {
        component.inject(this)
    }


    override suspend fun getDeviceListFlow(): Flow<List<DeviceModelBluetooth>> {
        if (!scanStateFlow.value) {
            deviceListFlow.value = arrayListOf()
            CoroutineScope(Dispatchers.IO).launch {
                launch {
                    scanStateFlow.value = true
                    scanDisposable = bluetooth.client.scanBleDevices(ScanSettings.Builder().build())
                        .subscribe(
                            {
                                if (it.bleDevice.name != null) {
                                    deviceSet.add(
                                        DeviceModelBluetooth(
                                            it.bleDevice.name!!,
                                            it.bleDevice.macAddress
                                        )
                                    )
                                }
                                if (deviceSet.size > deviceListFlow.value.size) {
                                    deviceListFlow.value = deviceSet.toList()
                                }
                            },
                            {
                                Log.e("ScanError", it.toString())
                            }
                        )
                }
                launch {
                    delay(10000)
                    stopScan()
                }
            }
        }
        return deviceListFlow
    }

    override suspend fun getScanState(): Flow<Boolean> {
        return scanStateFlow
    }

    override suspend fun stopScan() {
        scanDisposable.dispose()
        if (scanStateFlow.value) {
            scanStateFlow.value = false
        }
    }

    override suspend fun connectionToPeripheral(MAC: String) {
        bluetooth.connectDevice(MAC)
    }

    override suspend fun getConnectionStateFlow(): Flow<String> {
        return bluetooth.getConnectionFlow()
    }

    override suspend fun getConnectionState(): String {
        return bluetooth.connectionState
    }

    override suspend fun disconnectDevice() {
        bluetooth.disconnectDevice()
    }
}