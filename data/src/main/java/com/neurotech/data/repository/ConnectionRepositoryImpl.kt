package com.neurotech.data.repository

import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.connection.Connection
import com.neurotech.data.modules.settings.SettingsApi
import com.neurotech.domain.models.DeviceDomainModel
import com.neurotech.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectionRepositoryImpl: ConnectionRepository {

    @Inject
    lateinit var connection: Connection

    init {
        component.inject(this)
    }

    override suspend fun getDeviceListFlow(): Flow<List<DeviceDomainModel>> {
        return flow {
            connection.getDeviceListFlow().collect{
                emit(it.map { device -> DeviceDomainModel(device.name, device.MAC) })
            }
        }
    }

    override suspend fun getScanState(): Flow<Boolean> {
        return connection.getScanState()
    }

    override suspend fun stopScan() {
        connection.stopScan()
    }

    override suspend fun connectionToPeripheral(MAC: String) {
        connection.connectionToPeripheral(MAC)
    }

    override suspend fun getConnectionStateFlow(): Flow<String> {
        return connection.getConnectionStateFlow()
    }

    override suspend fun getConnectionState(): String {
        return connection.getConnectionState()
    }

    override suspend fun disconnectDevice() {
        connection.disconnectDevice()
    }
}