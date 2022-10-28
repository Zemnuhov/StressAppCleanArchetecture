package com.neurotech.data.modules.bluetooth.connection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.neurotech.data.DataModuleLog.appLog
import com.neurotech.data.di.RepositoryDI
import com.neurotech.data.modules.bluetooth.AppBluetoothManager
import com.neurotech.data.modules.settings.SettingsApi
import com.neurotech.domain.BleConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import no.nordicsemi.android.ble.ktx.stateAsFlow
import javax.inject.Inject

class BleConnection : BluetoothConnection {

    @Inject
    lateinit var manager: AppBluetoothManager

    val settings = SettingsApi()
    private val defaultMAC = "00:00:00:00:00:00"

    init {
        RepositoryDI.component.inject(this)
    }

    override suspend fun connectionToPeripheral(device: BluetoothDevice) {
        manager.connectToDevice(device)
    }

    override suspend fun getConnectionStateFlow(): Flow<String> {
        return flow {
            manager.stateAsFlow().collect {
                appLog("State: $it")
                if (!it.isConnected) {
                    emit(BleConstant.DISCONNECTED)
                } else {
                    emit(BleConstant.CONNECTING)
                    if (it.isReady) {
                        emit(BleConstant.CONNECTED)
                    }
                }
            }
        }

    }


    @SuppressLint("SwitchIntDef")
    override suspend fun getConnectionState(): String {
        return when (manager.connectionState) {
            1 -> BleConstant.CONNECTING
            2 -> BleConstant.CONNECTED
            3 -> BleConstant.DISCONNECTING
            else -> BleConstant.DISCONNECTED
        }
    }

    override suspend fun disconnectDevice() {
        manager.disconnect().enqueue()
        settings.saveDevice(defaultMAC)
    }
}