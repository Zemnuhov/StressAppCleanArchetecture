package com.neurotech.data.modules.bluetooth.connection

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.Flow

interface BluetoothConnection {
    suspend fun connectionToPeripheral(device: BluetoothDevice)
    suspend fun getConnectionStateFlow(): Flow<String>
    suspend fun getConnectionState():String
    suspend fun disconnectDevice()
}