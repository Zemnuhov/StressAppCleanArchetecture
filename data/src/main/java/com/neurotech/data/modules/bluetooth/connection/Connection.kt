package com.neurotech.data.modules.bluetooth.connection

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface Connection {
    suspend fun getDeviceListFlow(): Flow<List<DeviceModelBluetooth>>
    suspend fun getScanState():Flow<Boolean>
    suspend fun stopScan()
    suspend fun connectionToPeripheral(MAC: String)
    suspend fun getConnectionStateFlow():Flow<String>
    suspend fun getConnectionState():String
    suspend fun disconnectDevice()
}