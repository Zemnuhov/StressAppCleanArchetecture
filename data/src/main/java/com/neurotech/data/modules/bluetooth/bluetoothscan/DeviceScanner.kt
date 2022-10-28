package com.neurotech.data.modules.bluetooth.bluetoothscan

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.Flow

interface DeviceScanner {
    suspend fun getDeviceListFlow(): Flow<List<DeviceModelBluetooth>>
    suspend fun getScanState():Flow<Boolean>
    suspend fun stopScan()
    suspend fun getBluetoothDeviceByMac(mac: String): BluetoothDevice?
}