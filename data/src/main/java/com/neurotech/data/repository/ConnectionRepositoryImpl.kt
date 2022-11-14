package com.neurotech.data.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import com.neurotech.data.di.RepositoryDI.Companion.component
import com.neurotech.data.modules.bluetooth.bluetoothscan.DeviceScanner
import com.neurotech.data.modules.bluetooth.connection.BluetoothConnection
import com.neurotech.domain.models.DeviceDomainModel
import com.neurotech.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectionRepositoryImpl: ConnectionRepository {

    @Inject
    lateinit var connection: BluetoothConnection
    @Inject
    lateinit var scanner: DeviceScanner

    @Inject
    lateinit var context: Context


    init {
        component.inject(this)
    }

    override suspend fun getDeviceListFlow(): Flow<List<DeviceDomainModel>> {
        return flow {
            scanner.getDeviceListFlow().collect{
                emit(it.map { device -> DeviceDomainModel(device.name, device.MAC) })
            }
        }
    }

    override suspend fun getScanState(): Flow<Boolean> {
        return scanner.getScanState()
    }

    override suspend fun stopScan() {
        scanner.stopScan()
    }

    override suspend fun connectionToPeripheral(MAC: String) {
        val bluetoothManager: BluetoothManager? = getSystemService(context, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
        if(bluetoothAdapter != null){
            connection.connectionToPeripheral(bluetoothAdapter.getRemoteDevice(MAC))
        }
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