package com.neurotech.test.bluetooth.connection

import kotlinx.coroutines.flow.Flow

interface Connection {
    suspend fun getDeviceListFlow(): Flow<List<DeviceModelBluetooth>>
    suspend fun getScanState():Flow<Boolean>
    suspend fun stopScan()
    suspend fun connectionToPeripheral(MAC: String)
    suspend fun getConnectionStateFlow():Flow<String>
    suspend fun disconnectDevice()
}