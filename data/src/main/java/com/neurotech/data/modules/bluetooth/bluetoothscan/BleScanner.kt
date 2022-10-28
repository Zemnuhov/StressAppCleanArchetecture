package com.neurotech.data.modules.bluetooth.bluetoothscan

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import no.nordicsemi.android.support.v18.scanner.*

class BleScanner: DeviceScanner {
    private val scanner = BluetoothLeScannerCompat.getScanner()
    private val scanCallback = AppScanCallback()

    private val deviceList = mutableListOf<DeviceModelBluetooth>()
    private val deviceListFlow = MutableStateFlow(listOf<DeviceModelBluetooth>())
    private val scanStateFlow = MutableStateFlow(false)
    private val bluetoothDeviceList = mutableListOf<BluetoothDevice>()

    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun getDeviceListFlow(): Flow<List<DeviceModelBluetooth>> {
        val settings: ScanSettings = ScanSettings.Builder()
            .setLegacy(false)
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setUseHardwareBatchingIfSupported(true)
            .build()
        val filters: MutableList<ScanFilter> = ArrayList()
        //filters.add(ScanFilter.Builder().setServiceUuid(ParcelUuid(serviceUUID)).build())
        scanner.startScan(filters, settings, scanCallback )
        scanStateFlow.value = true
        scope.launch {
            delay(10000)
            stopScan()
        }
        return deviceListFlow
    }

    override suspend fun getScanState(): Flow<Boolean> {
        return scanStateFlow
    }

    override suspend fun stopScan() {
        scanner.stopScan(scanCallback)
        scanStateFlow.value = false
    }

    override suspend fun getBluetoothDeviceByMac(mac: String): BluetoothDevice? {
        bluetoothDeviceList.forEach {
            if(it.address == mac){
                return it
            }
        }
        return null
    }

    inner class AppScanCallback: ScanCallback(){
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if(result.device.address !in deviceList.map { it.MAC } && result.device.name != null){
                bluetoothDeviceList.add(result.device)
                deviceList.add(DeviceModelBluetooth(result.device.name, result.device.address))
                deviceListFlow.value = deviceList
            }
        }
    }
}