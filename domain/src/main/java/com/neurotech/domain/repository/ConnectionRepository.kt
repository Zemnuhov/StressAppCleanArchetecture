package com.neurotech.domain.repository

import com.neurotech.domain.models.DeviceDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ConnectionRepository {
    suspend fun getDeviceListFlow(): Flow<List<DeviceDomainModel>>
    suspend fun getScanState(): Flow<Boolean>
    suspend fun stopScan()
    suspend fun connectionToPeripheral(MAC: String)
    suspend fun getConnectionStateFlow():Flow<String>
    suspend fun getConnectionState():String
    suspend fun disconnectDevice()
}